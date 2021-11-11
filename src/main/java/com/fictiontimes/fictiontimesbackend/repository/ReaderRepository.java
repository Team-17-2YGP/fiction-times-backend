package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Types.SubscriptionStatus;
import com.fictiontimes.fictiontimesbackend.model.User;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReaderRepository {

    private PreparedStatement statement;

    public void verifyReaderSubscription(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement("UPDATE reader SET subscriptionStatus = ? WHERE userId = ?");
            statement.setString(1, SubscriptionStatus.VERIFIED.toString());
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<User> getFollowingWritersList(int userId, int limit) throws DatabaseOperationException {
        try {
            List<User> writerList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT userId, firstName, lastName, profilePictureUrl " +
                            "FROM user " +
                            "WHERE userId IN (SELECT writerId FROM reader_following where readerId=?)" +
                            "LIMIT ?"
            );
            statement.setInt(1, userId);
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                User writer = new User();
                writer.setUserId(resultSet.getInt("userId"));
                writer.setFirstName(resultSet.getString("firstName"));
                writer.setLastName(resultSet.getString("lastName"));
                writer.setProfilePictureUrl(resultSet.getString("profilePictureUrl"));

                writerList.add(writer);
            }
            return writerList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void followWriter(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO reader_following VALUES (?, ?, ?)"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);
            statement.setBoolean(3, false);

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void unfollowWriter(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM reader_following WHERE readerId=? AND writerId=?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public boolean getFollowingStatus(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT writerId FROM reader_following WHERE readerId=? AND writerId=?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public boolean getNotificationStatus(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT notification FROM reader_following WHERE readerId=? AND writerId=?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("notification");
            }
            return false;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void setNotificationStatus(int readerId, int writerId, boolean notificationStatus) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE reader_following SET notification=? WHERE readerId=? AND writerId=?"
            );
            statement.setBoolean(1, notificationStatus);
            statement.setInt(2, readerId);
            statement.setInt(3, writerId);

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
