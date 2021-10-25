package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.model.Types.StoryStatus;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.FileUtils;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StoryRepository {
    private PreparedStatement statement;

    public Story createNewStory(Story story) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement(
                "INSERT INTO story (userId, likeCount, title, coverArtUrl, status, releasedDate, description, tags) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?)",
                statement.RETURN_GENERATED_KEYS);
        story.setCoverArtUrl(FileUtils.saveFile(story.getCoverArt()));
        statement.setInt(1, story.getUserId());
        statement.setInt(2, story.getLikeCount());
        statement.setString(3, story.getTitle());
        statement.setString(4, story.getCoverArtUrl());
        statement.setString(5, story.getStatus().toString());
        statement.setObject(6, story.getReleasedDate());
        statement.setString(7, story.getDescription());
        statement.setString(8, CommonUtils.getGson().toJson(story.getTags()));

        statement.execute();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            story.setStoryId(resultSet.getInt(1));
        }

        batchInsertGenres(story.getGenres(), story.getStoryId());

        return story;
    }

    private void batchInsertGenres(List<Genre> genreList, int storyId) throws SQLException, IOException, ClassNotFoundException {
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
    }

    /** Get stories created by the writer */
    public List<Story> getStoryListByUserId(int userId) throws SQLException, IOException, ClassNotFoundException {
        List<Story> storyList = new ArrayList<>();
        statement = DBConnection.getConnection().prepareStatement(
                "SELECT * FROM story WHERE userId = ?"
        );
        statement.setInt(1, userId);

        Type tagListType = new TypeToken<ArrayList<String>>() {}.getType();

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
    }

    private List<Genre> getStoryGenreList(int storyId) throws SQLException, IOException, ClassNotFoundException {
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
    }

    public Story getStoryById(int storyId) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement(
                "SELECT * FROM story WHERE storyId = ?"
        );
        statement.setInt(1, storyId);
        ResultSet resultSet = statement.executeQuery();

        Type tagListType = new TypeToken<ArrayList<String>>() {}.getType();
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
    }
}
