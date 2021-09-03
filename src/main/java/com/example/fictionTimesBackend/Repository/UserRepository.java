package com.example.fictionTimesBackend.Repository;

import com.example.fictionTimesBackend.Model.User;

import java.io.IOException;
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
}
