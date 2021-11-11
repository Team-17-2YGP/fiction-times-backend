package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.Writer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WriterRepository {

    private PreparedStatement statement;

    public Writer findWriterById(int writerId) throws DatabaseOperationException {
        try {
            Writer writer = new Writer();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN writer w ON u.userId=w.userId WHERE u.userId=?"
            );
            statement.setInt(1, writerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                writer.setUserId(resultSet.getInt(1));
                writer.setUserName(resultSet.getString(2));
                writer.setFirstName(resultSet.getString(3));
                writer.setLastName(resultSet.getString(4));
                writer.setPassword(resultSet.getString(5));
                writer.setEmail(resultSet.getString(6));
                writer.setAddressLane1(resultSet.getString(7));
                writer.setAddressLane2(resultSet.getString(8));
                writer.setCity(resultSet.getString(9));
                writer.setCountry(resultSet.getString(10));
                writer.setPhoneNumber(resultSet.getString(11));
                writer.setProfilePictureUrl(resultSet.getString(12));
                writer.setUserType(UserType.valueOf(resultSet.getString(13)));
                writer.setUserStatus(UserStatus.valueOf(resultSet.getString(14)));
                writer.setBusinessAddressLane1(resultSet.getString(16));
                writer.setBusinessAddressLane2(resultSet.getString(17));
                writer.setBusinessCity(resultSet.getString(18));
                writer.setBusinessCountry(resultSet.getString(19));
                writer.setLandline(resultSet.getString(20));
                writer.setCurrentBalance(resultSet.getInt(21));
                writer.setBio(resultSet.getString(22));
                return writer;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public int getFollowerCountById(int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT COUNT(readerId) FROM reader_following WHERE writerId=?"
            );
            statement.setInt(1, writerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("COUNT(readerId)");
            }
            return 0;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public int getStoryCountById(int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT COUNT(storyId) FROM story WHERE userId=?"
            );
            statement.setInt(1, writerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("COUNT(storyId)");
            }
            return 0;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
