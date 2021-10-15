package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminRepository {

    private PreparedStatement statement;

    public List<WriterApplicant> getApplicantList() throws SQLException, IOException, ClassNotFoundException {
        List<WriterApplicant> applicantList = new ArrayList<>();
        statement = DBConnection.getConnection().prepareStatement(
                "SELECT * FROM writerApplicant"
        );
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()) {
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
    }

    public WriterApplicant getApplicantByUserId(int userId) throws SQLException, IOException, ClassNotFoundException {
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
    }
}
