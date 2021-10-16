package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.model.Genre;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.FileUtils;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
