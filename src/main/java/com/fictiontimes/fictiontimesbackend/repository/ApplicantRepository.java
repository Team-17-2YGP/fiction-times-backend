package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;

import java.io.IOException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicantRepository {

    private final UserRepository userRepository;
    private PreparedStatement statement;

    public ApplicantRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public WriterApplicant getWriterApplicantById(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "Select * from writerApplicant where userId = ?"
            );
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                WriterApplicant applicant = new WriterApplicant();
                applicant.setUserId(resultSet.getInt("userId"));
                applicant.setBusinessAddressCity(resultSet.getString("city"));
                applicant.setBusinessAddressCountry(resultSet.getString("country"));
                applicant.setBusinessAddressLane1(resultSet.getString("addressLane1"));
                applicant.setBusinessAddressLane2(resultSet.getString("addressLane2"));
                applicant.setLandline(resultSet.getString("landline"));
                applicant.setResponse(resultSet.getString("response"));
                applicant.setRespondedAt(resultSet.getDate("respondedAt"));
                applicant.setRequestedAt(resultSet.getDate("requestedAt"));
                applicant.setSocialMediaUrls(resultSet.getString("socialMediaUrls"));
                // TODO: figure out a way to send the downloaded previous work file to the frontend
                applicant.setPreviousWorkUrl(resultSet.getString("previousWork"));
                User correspondingUser = userRepository.findUserByUserId(userId);
                applicant.setUserName(correspondingUser.getUserName());
                applicant.setFirstName(correspondingUser.getFirstName());
                applicant.setLastName(correspondingUser.getLastName());
                applicant.setEmail(correspondingUser.getEmail());
                applicant.setAddressLane1(correspondingUser.getAddressLane1());
                applicant.setAddressLane2(correspondingUser.getAddressLane2());
                applicant.setCity(correspondingUser.getCity());
                applicant.setCountry(correspondingUser.getCountry());
                applicant.setPhoneNumber(correspondingUser.getPhoneNumber());
                applicant.setProfilePictureUrl(correspondingUser.getProfilePictureUrl());
                applicant.setUserType(correspondingUser.getUserType());
                applicant.setUserStatus(correspondingUser.getUserStatus());
                return applicant;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void updateRequestedAt(WriterApplicant applicant) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE writerApplicant SET requestedAt = ? WHERE userId = ?"
            );
            statement.setDate(1, new Date(new java.util.Date().getTime()));
            statement.setInt(2, applicant.getUserId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
