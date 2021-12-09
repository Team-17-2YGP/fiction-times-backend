package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.FileUtils;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoryRepository {
    private PreparedStatement statement;

    public Story createNewStory(Story story) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO story (userId, likeCount, title, status, releasedDate, description, tags) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)",
                    statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, story.getUserId());
            statement.setInt(2, story.getLikeCount());
            statement.setString(3, story.getTitle());
            statement.setString(4, story.getStatus().toString());
            statement.setObject(5, story.getReleasedDate());
            statement.setString(6, story.getDescription());
            statement.setString(7, CommonUtils.getGson().toJson(story.getTags()));

            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                story.setStoryId(resultSet.getInt(1));
                story.setCoverArtUrl(updateStoryCoverArt(story.getStoryId(), story.getCoverArt()));
                batchInsertGenres(story.getGenres(), story.getStoryId());
                return story;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public String updateStoryCoverArt(int storyId, Part coverArtFile) throws DatabaseOperationException {
        try {
            String coverArtUrl = FileUtils.saveFile(coverArtFile, "storyCover/story-cover-" + storyId);
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE story SET coverArtUrl=? WHERE storyId=?"
            );
            statement.setString(1, coverArtUrl);
            statement.setInt(2, storyId);
            statement.execute();
            return coverArtUrl;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    private void batchInsertGenres(List<Genre> genreList, int storyId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO story_genre (storyId, genreId) " +
                            "VALUES(?, ?)"
            );
            int i = 0;

            for (Genre genre : genreList) {
                statement.setInt(1, storyId);
                statement.setInt(2, genre.getGenreId());
                statement.addBatch();
                i++;
                if (i % 1000 == 0 || i == genreList.size()) {
                    statement.executeBatch(); // Execute every 1000 items.
                }
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    /**
     * Get stories created by the writer
     */
    public List<Story> getStoryListByUserId(int userId) throws DatabaseOperationException {
        try {
            List<Story> storyList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM story WHERE userId = ?"
            );
            statement.setInt(1, userId);

            Type tagListType = new TypeToken<ArrayList<String>>() {
            }.getType();

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int storyId = resultSet.getInt("storyId");
                List<String> tags = CommonUtils.getGson().fromJson(resultSet.getString("tags"), tagListType);
                List<Genre> genres = getStoryGenreList(storyId);
                Story story = new Story(
                        storyId,
                        resultSet.getInt("userId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("likeCount"),
                        resultSet.getString("coverArtUrl"),
                        null,
                        StoryStatus.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("releasedDate"),
                        tags,
                        genres
                );
                storyList.add(story);
            }
            return storyList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Genre> getStoryGenreList(int storyId) throws DatabaseOperationException {
        try {
            List<Genre> genreList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM genre WHERE genreId IN " +
                            "(SELECT genreId FROM story_genre WHERE storyId = ?)"
            );
            statement.setInt(1, storyId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Genre genre = new Genre(
                        resultSet.getInt("genreId"),
                        resultSet.getString("genreName")
                );
                genreList.add(genre);
            }
            return genreList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public Story getStoryById(int storyId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM story WHERE storyId = ?"
            );
            statement.setInt(1, storyId);
            ResultSet resultSet = statement.executeQuery();

            Type tagListType = new TypeToken<ArrayList<String>>() {
            }.getType();
            if (resultSet.next()) {
                List<String> tags = CommonUtils.getGson().fromJson(resultSet.getString("tags"), tagListType);
                List<Genre> genres = getStoryGenreList(storyId);
                return new Story(
                        storyId,
                        resultSet.getInt("userId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("likeCount"),
                        resultSet.getString("coverArtUrl"),
                        null,
                        StoryStatus.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("releasedDate"),
                        tags,
                        genres
                );
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Story> getRecentlyReleasedStories(int limit) throws DatabaseOperationException {
        try {
            List<Story> stories = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM story ORDER BY releasedDate DESC LIMIT ?"
            );
            statement.setInt(1, limit);
            ResultSet resultSet = statement.executeQuery();
            Type tagListType = new TypeToken<ArrayList<String>>() {}.getType();
            while (resultSet.next()) {
                stories.add(new Story(
                        resultSet.getInt("storyId"),
                        resultSet.getInt("userId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("likeCount"),
                        resultSet.getString("coverArtUrl"),
                        null,
                        StoryStatus.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("releasedDate"),
                        CommonUtils.getGson().fromJson(resultSet.getString("tags"), tagListType),
                        getStoryGenreList(resultSet.getInt("storyId"))
                ));
            }
            return stories;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void saveEpisode(Episode episode) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO episode(storyId, episodeNumber, title, description, readCount, uploadedAt, content) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, episode.getStoryId());
            statement.setInt(2, episode.getEpisodeNumber());
            statement.setString(3, episode.getTitle());
            statement.setString(4, episode.getDescription());
            statement.setInt(5, episode.getReadCount());
            statement.setDate(6, new Date(episode.getUploadedAt().getTime()));
            statement.setString(7, episode.getContent());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Episode> getEpisodeListByStoryId(int storyId) throws DatabaseOperationException {
        try {
            List<Episode> episodes = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT episodeId, storyId, episodeNumber, readCount, uploadedAt, title, description " +
                            "FROM episode " +
                            "WHERE storyId = ?"
            );
            statement.setInt(1, storyId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                episodes.add(new Episode(
                        resultSet.getInt("episodeId"),
                        resultSet.getInt("storyId"),
                        resultSet.getInt("episodeNumber"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("readCount"),
                        resultSet.getTimestamp("uploadedAt"),
                        null
                ));
            }
            return episodes;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
