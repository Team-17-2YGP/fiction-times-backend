package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.model.Story;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StoryRepository {
    private PreparedStatement statement;

    public Story createNewStory(Story story) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement(
                "INSERT INTO story (userId, likeCount, title, coverArtUrl, status, releasedDate, description) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)",
                statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, story.getUserId());
        statement.setInt(2, story.getLikeCount());
        statement.setString(3, story.getTitle());
        statement.setString(4, story.getCoverArtUrl());
        statement.setString(5, story.getStatus().toString());
        statement.setObject(6, story.getReleasedDate());
        statement.setString(7, story.getDescription());

        statement.execute();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            story.setStoryId(resultSet.getInt(1));
        }
        return story;
    }
}
