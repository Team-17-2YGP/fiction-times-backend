package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {

    private PreparedStatement statement;

    public List<WriterApplicant> getApplicantList() throws DatabaseOperationException {
        try {
            List<WriterApplicant> applicantList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM writerApplicant ORDER BY requestedAt DESC"
            );
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                WriterApplicant applicant = new WriterApplicant();
                applicant.setUserId(resultSet.getInt("userId"));
                applicant.setBusinessAddressLane1(resultSet.getString("addressLane1"));
                applicant.setBusinessAddressLane2(resultSet.getString("addressLane2"));
                applicant.setBusinessAddressCity(resultSet.getString("city"));
                applicant.setBusinessAddressCountry(resultSet.getString("country"));
                applicant.setRequestedAt(resultSet.getDate("requestedAt"));
                applicant.setResponse(resultSet.getString("response"));
                applicant.setRespondedAt(resultSet.getDate("respondedAt"));
                applicant.setPreviousWorkUrl(resultSet.getString("previousWork"));
                applicant.setLandline(resultSet.getString("landline"));
                applicant.setSocialMediaUrls(resultSet.getString("socialMediaUrls"));
                applicantList.add(applicant);
            }
            return applicantList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public WriterApplicant getApplicantByUserId(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM writerApplicant WHERE userId = ?"
            );
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                WriterApplicant applicant = new WriterApplicant();
                applicant.setUserId(resultSet.getInt("userId"));
                applicant.setBusinessAddressLane1(resultSet.getString("addressLane1"));
                applicant.setBusinessAddressLane2(resultSet.getString("addressLane2"));
                applicant.setBusinessAddressCity(resultSet.getString("city"));
                applicant.setBusinessAddressCountry(resultSet.getString("country"));
                applicant.setRequestedAt(resultSet.getDate("requestedAt"));
                applicant.setResponse(resultSet.getString("response"));
                applicant.setRespondedAt(resultSet.getDate("respondedAt"));
                applicant.setPreviousWorkUrl(resultSet.getString("previousWork"));
                applicant.setLandline(resultSet.getString("landline"));
                applicant.setSocialMediaUrls(resultSet.getString("socialMediaUrls"));
                return applicant;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void setApplicantAdminResponse(WriterApplicant applicant) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE writerApplicant SET response = ?, respondedAt = ?, requestedAt = ? WHERE userId = ?"
            );
            statement.setString(1, applicant.getResponse());
            statement.setDate(2, new Date(applicant.getRespondedAt().getTime()));
            statement.setDate(3, new Date(applicant.getRequestedAt().getTime()));
            statement.setInt(4, applicant.getUserId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void deleteApplicant(WriterApplicant applicant) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM writerApplicant WHERE userId = ?"
            );
            statement.setInt(1, applicant.getUserId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void changeApplicantUserType(WriterApplicant applicant) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE user SET userType = ? WHERE userId = ?"
            );
            statement.setString(1, UserType.WRITER.toString());
            statement.setInt(2, applicant.getUserId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void createNewWriter(WriterApplicant applicant) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO writer (userId, landline, addressLane1, addressLane2, city, country)" +
                            "VALUES(?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, applicant.getUserId());
            statement.setString(2, applicant.getLandline());
            statement.setString(3, applicant.getBusinessAddressLane1());
            statement.setString(4, applicant.getBusinessAddressLane2());
            statement.setString(5, applicant.getBusinessAddressCity());
            statement.setString(6, applicant.getBusinessAddressCountry());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void deleteUser(WriterApplicant applicant) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM user WHERE userId = ?"
            );
            statement.setInt(1, applicant.getUserId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void blockUserByUserId(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE user SET userStatus = ? WHERE userId = ?"
            );
            statement.setString(1, UserStatus.BANNED.toString());
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
