package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderStoryDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.StoryRatingDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.StoryReviewDTO;
import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.FileUtils;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
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

    public void updateStoryDescription(Story story) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE story SET description = ? WHERE storyId = ?"
            );
            statement.setString(1, story.getDescription());
            statement.setInt(2, story.getStoryId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void deleteStory(int storyId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM story WHERE storyId = ?"
            );
            statement.setInt(1, storyId);
            statement.execute();
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
                StoryRatingDTO rating = getStoryRating(storyId);
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
                        genres,
                        rating.getReviewerCount(),
                        rating.getAverageRating()
                );
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public StoryRatingDTO getStoryRating(int storyId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT AVG(rating), COUNT(*) FROM story_review WHERE storyId = ?"
            );
            statement.setInt(1, storyId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new StoryRatingDTO(
                        resultSet.getInt("COUNT(*)"),
                        resultSet.getFloat("AVG(rating)")
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

    public void updateStoryLikeCount(int storyId, boolean increment) throws DatabaseOperationException {
        try {
            Connection con = DBConnection.getConnection();
            try {
                con.setAutoCommit(false);
                statement = con.prepareStatement(
                        "SELECT likeCount FROM story WHERE storyId = ?"
                );
                statement.setInt(1, storyId);
                ResultSet resultSet = statement.executeQuery();
                resultSet.next();
                int likeCount = resultSet.getInt("likeCount");
                if (increment) likeCount++;
                else likeCount--;

                statement = con.prepareStatement(
                        "UPDATE story SET likeCount = ? WHERE storyId = ?"
                );
                statement.setInt(1, likeCount);
                statement.setInt(2, storyId);
                statement.executeUpdate();

                con.commit();
            } catch (SQLException e) {
                throw new DatabaseOperationException(e.getMessage());
            } finally {
                con.setAutoCommit(true);
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<ReaderStoryDTO> getLikedStoriesList(int readerId, int limit) throws DatabaseOperationException {
        try {
            List<ReaderStoryDTO> storyList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * " +
                            "FROM story s " +
                            "INNER JOIN story_like sl ON s.storyId = sl.storyId " +
                            "WHERE sl.readerId = ? " +
                            "ORDER BY sl.timestamp DESC " +
                            "LIMIT ?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();
            Type tagListType = new TypeToken<ArrayList<String>>() {}.getType();
            while (resultSet.next()) {
                storyList.add(new ReaderStoryDTO(new Story(
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
                )));
            }
            return storyList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public boolean isLikedStory(int readerId, int storyId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM  story_like WHERE readerId =? AND storyId = ?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, storyId);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<StoryReviewDTO> getStoryReviewList(int storyId, int limit, int offset) throws DatabaseOperationException {
        try {
            List<StoryReviewDTO> storyReviews = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * " +
                            "FROM story_review sr " +
                            "INNER JOIN user u ON sr.readerId = u.userId " +
                            "WHERE sr.storyId = ? " +
                            "ORDER BY sr.timestamp DESC " +
                            "LIMIT ? OFFSET ?"
            );
            statement.setInt(1, storyId);
            statement.setInt(2, limit);
            statement.setInt(3, offset);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Reader reader = new Reader();
                reader.setUserId(resultSet.getInt("userId"));
                reader.setUserName(resultSet.getString("userName"));
                reader.setFirstName(resultSet.getString("firstName"));
                reader.setLastName(resultSet.getString("lastName"));
                reader.setProfilePictureUrl(resultSet.getString("profilePictureUrl"));
                storyReviews.add(new StoryReviewDTO(
                        resultSet.getInt("storyId"),
                        reader,
                        resultSet.getInt("rating"),
                        resultSet.getString("review"),
                        resultSet.getTimestamp("timestamp")
                ));
            }
            return storyReviews;
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

    public String getEpisodeContentByEpisodeId(int episodeId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT content FROM episode WHERE episodeId = ?"
            );
            statement.setInt(1, episodeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String episode = resultSet.getString("content");
                return episode;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Episode> getBookmarkList(int readerId) throws DatabaseOperationException {
        try {
            List<Episode> bookmarkList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM bookmark b INNER JOIN episode e on b.episodeId = e.episodeId " +
                            "WHERE b.readerId = ? ORDER BY b.timestamp DESC"
            );
            statement.setInt(1, readerId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Episode episode = new Episode();
                episode.setStoryId(resultSet.getInt("storyId"));
                episode.setEpisodeId(resultSet.getInt("episodeId"));
                episode.setEpisodeNumber(resultSet.getInt("episodeNUmber"));
                episode.setTitle(resultSet.getString("title"));
                episode.setDescription(resultSet.getString("description"));
                episode.setReadCount(resultSet.getInt("readCount"));
                episode.setUploadedAt(resultSet.getTimestamp("uploadedAt"));
                bookmarkList.add(episode);
            }
            return bookmarkList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
