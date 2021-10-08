package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.utils.FileUtils;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {

    private PreparedStatement statement;

    public User createNewUser(User user) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement(
                "INSERT INTO user (userName, firstName, lastName, password, email, addressLane1, " +
                        "addressLane2, city, country, phoneNumber, profilePictureUrl, userType, userStatus) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                statement.RETURN_GENERATED_KEYS);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getFirstName());
        statement.setString(3, user.getLastName());
        statement.setString(4, user.getPassword());
        statement.setString(5, user.getEmail());
        statement.setString(6, user.getAddressLane1());
        statement.setString(7, user.getAddressLane2());
        statement.setString(8, user.getCity());
        statement.setString(9, user.getCountry());
        statement.setString(10, user.getPhoneNumber());
        statement.setString(11, user.getProfilePictureUrl());
        statement.setString(12, user.getUserType().toString());
        statement.setString(13, user.getUserStatus().toString());
        // execute the statement
        statement.execute();
        ResultSet resultSet = statement.getGeneratedKeys();
        if (resultSet.next()) {
            user.setUserId(resultSet.getInt(1));
        }
        return user;
    }

    public User findUserByUserName(String userName) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE userName = ?");
        statement.setString(1, userName);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("userName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("addressLane1"),
                    resultSet.getString("addressLane2"),
                    resultSet.getString("city"),
                    resultSet.getString("country"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("profilePictureUrl"),
                    UserType.valueOf(resultSet.getString("userType")),
                    UserStatus.valueOf(resultSet.getString("userStatus"))
            );
        }
        return null;
    }

    public User findUserByEmail(String email) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE email = ?");
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("userName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("addressLane1"),
                    resultSet.getString("addressLane2"),
                    resultSet.getString("city"),
                    resultSet.getString("country"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("profilePictureUrl"),
                    UserType.valueOf(resultSet.getString("userType")),
                    UserStatus.valueOf(resultSet.getString("userStatus"))
            );
        }
        return null;
    }

    public WriterApplicant registerWriterApplicant(WriterApplicant applicant) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement(
                "INSERT INTO writerApplicant (userId, response, respondedAt, requestedAt, previousWork, " +
                        "socialMediaUrls, landline, addressLane1, addressLane2, city, country)" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );
        statement.setInt(1, applicant.getUserId());
        statement.setString(2, applicant.getResponse());
        statement.setDate(3, null);
        statement.setDate(4, new Date(applicant.getRequestedAt().getTime()));
        statement.setString(5, FileUtils.saveFile(applicant.getPreviousWork()));
        statement.setString(6, applicant.getSocialMediaUrls());
        statement.setString(7, applicant.getLandline());
        statement.setString(8, applicant.getBusinessAddressLane1());
        statement.setString(9, applicant.getBusinessAddressLane2());
        statement.setString(10, applicant.getBusinessAddressCity());
        statement.setString(11, applicant.getBusinessAddressCountry());
        statement.execute();
        return applicant;
    }

    public Reader registerReader(Reader reader) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement(
                "INSERT INTO reader (userId, subscriptionStatus) VALUES (?, ?)"
        );
        statement.setInt(1, reader.getUserId());
        statement.setString(2, reader.getSubscriptionStatus().toString());
        statement.execute();
        return reader;
    }

    public User findUserByUserId(int userId) throws SQLException, IOException, ClassNotFoundException {
        statement = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE userId = ?");
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();
        if (resultSet.next()) {
            return new User(
                    resultSet.getInt("userId"),
                    resultSet.getString("userName"),
                    resultSet.getString("firstName"),
                    resultSet.getString("lastName"),
                    resultSet.getString("password"),
                    resultSet.getString("email"),
                    resultSet.getString("addressLane1"),
                    resultSet.getString("addressLane2"),
                    resultSet.getString("city"),
                    resultSet.getString("country"),
                    resultSet.getString("phoneNumber"),
                    resultSet.getString("profilePictureUrl"),
                    UserType.valueOf(resultSet.getString("userType")),
                    UserStatus.valueOf(resultSet.getString("userStatus"))
            );
        }
        return null;
    }
}
