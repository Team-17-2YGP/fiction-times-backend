package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
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
import java.util.ArrayList;
import java.util.List;

public class UserRepository {

    private PreparedStatement statement;

    public User createNewUser(User user) throws DatabaseOperationException {
        try {
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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public User findUserByUserName(String userName) throws DatabaseOperationException {
        try {
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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public User findUserByEmail(String email) throws DatabaseOperationException {
        try {
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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public WriterApplicant registerWriterApplicant(WriterApplicant applicant) throws DatabaseOperationException {
        try {
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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public Reader registerReader(Reader reader) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO reader (userId, subscriptionStatus) VALUES (?, ?)"
            );
            statement.setInt(1, reader.getUserId());
            statement.setString(2, reader.getSubscriptionStatus().toString());
            statement.execute();
            return reader;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public User findUserByUserId(int userId) throws DatabaseOperationException {
        try {
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
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<User> getAdminUsers() {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user WHERE userType = ?"
            );
            statement.setString(1, UserType.ADMIN.toString());
            ResultSet resultSet = statement.executeQuery();
            List<User> adminUsers = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setAddressLane1(resultSet.getString("addressLane1"));
                user.setAddressLane2(resultSet.getString("addressLane2"));
                user.setCity(resultSet.getString("city"));
                user.setCountry(resultSet.getString("country"));
                user.setPhoneNumber(resultSet.getString("phoneNumber"));
                user.setProfilePictureUrl(resultSet.getString("profilePictureUrl"));
                user.setUserType(UserType.valueOf(resultSet.getString("userType")));
                user.setUserStatus(UserStatus.valueOf(resultSet.getString("userStatus")));
                adminUsers.add(user);
            }
            return adminUsers;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void activateUserProfile(User user) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE user SET userStatus = ? WHERE userId = ?"
            );
            statement.setString(1, UserStatus.ACTIVATED.toString());
            statement.setInt(2, user.getUserId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void updatePassword(int userId, String password) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE user SET password = ? WHERE userId = ?"
            );
            statement.setString(1, password);
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
