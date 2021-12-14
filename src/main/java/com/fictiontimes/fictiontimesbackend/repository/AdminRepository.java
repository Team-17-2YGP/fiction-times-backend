package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayoutAdminDTO;
import com.fictiontimes.fictiontimesbackend.model.Payout;
import com.fictiontimes.fictiontimesbackend.model.Types.PayoutStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.service.WriterService;

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

    public List<Writer> getWritersList(int limit) throws DatabaseOperationException {
        try {
            List<Writer> writerList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN writer w on u.userId = w.userId " +
                            "WHERE u.userType = ? ORDER BY u.userId DESC " +
                            "LIMIT ?"
            );
            statement.setString(1, UserType.WRITER.toString());
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Writer writer = new Writer();
                writer.setUserId(resultSet.getInt(1));
                writer.setUserName(resultSet.getString(2));
                writer.setFirstName(resultSet.getString(3));
                writer.setLastName(resultSet.getString(4));
                writer.setEmail(resultSet.getString(6));
                writer.setAddressLane1(resultSet.getString(7));
                writer.setAddressLane2(resultSet.getString(8));
                writer.setCity(resultSet.getString(9));
                writer.setCountry(resultSet.getString(10));
                writer.setPhoneNumber(resultSet.getString(11));
                writer.setProfilePictureUrl(resultSet.getString(12));
                writer.setUserStatus(UserStatus.valueOf(resultSet.getString(14)));
                writer.setBusinessAddressLane1(resultSet.getString(16));
                writer.setBusinessAddressLane2(resultSet.getString(17));
                writer.setBusinessCity(resultSet.getString(18));
                writer.setBusinessCountry(resultSet.getString(19));
                writer.setLandline(resultSet.getString(20));
                writer.setCurrentBalance(resultSet.getDouble(21));
                writer.setBio(resultSet.getString(22));
                writerList.add(writer);
            }
            return writerList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void unblockUserByUserId(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE user SET userStatus = ? WHERE userId = ?"
            );
            statement.setString(1, UserStatus.ACTIVATED.toString());
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Writer> searchWritersByName(String userName) throws DatabaseOperationException {
        try {
            List<Writer> writerList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN writer w on u.userId = w.userId " +
                            "WHERE u.userType = ? AND u.firstName LIKE ? OR u.lastName LIKE ? OR u.userName LIKE ? ORDER BY u.userId DESC"
            );
            statement.setString(1, UserType.WRITER.toString());
            statement.setString(2, "%"+userName+"%");
            statement.setString(3, "%"+userName+"%");
            statement.setString(4,"%"+userName+"%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Writer writer = new Writer();
                writer.setUserId(resultSet.getInt(1));
                writer.setUserName(resultSet.getString(2));
                writer.setFirstName(resultSet.getString(3));
                writer.setLastName(resultSet.getString(4));
                writer.setEmail(resultSet.getString(6));
                writer.setAddressLane1(resultSet.getString(7));
                writer.setAddressLane2(resultSet.getString(8));
                writer.setCity(resultSet.getString(9));
                writer.setCountry(resultSet.getString(10));
                writer.setPhoneNumber(resultSet.getString(11));
                writer.setProfilePictureUrl(resultSet.getString(12));
                writer.setUserStatus(UserStatus.valueOf(resultSet.getString(14)));
                writer.setBusinessAddressLane1(resultSet.getString(16));
                writer.setBusinessAddressLane2(resultSet.getString(17));
                writer.setBusinessCity(resultSet.getString(18));
                writer.setBusinessCountry(resultSet.getString(19));
                writer.setLandline(resultSet.getString(20));
                writer.setCurrentBalance(resultSet.getDouble(21));
                writer.setBio(resultSet.getString(22));
                writerList.add(writer);
            }
            return writerList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Writer> searchWritersById(int userId) throws DatabaseOperationException {
        try {
            List<Writer> writerList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN writer w on u.userId = w.userId " +
                            "WHERE u.userType = ? AND u.userId LIKE ? ORDER BY u.userId DESC"
            );
            statement.setString(1, UserType.WRITER.toString());
            statement.setString(2, "%"+userId+"%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Writer writer = new Writer();
                writer.setUserId(resultSet.getInt(1));
                writer.setUserName(resultSet.getString(2));
                writer.setFirstName(resultSet.getString(3));
                writer.setLastName(resultSet.getString(4));
                writer.setEmail(resultSet.getString(6));
                writer.setAddressLane1(resultSet.getString(7));
                writer.setAddressLane2(resultSet.getString(8));
                writer.setCity(resultSet.getString(9));
                writer.setCountry(resultSet.getString(10));
                writer.setPhoneNumber(resultSet.getString(11));
                writer.setProfilePictureUrl(resultSet.getString(12));
                writer.setUserStatus(UserStatus.valueOf(resultSet.getString(14)));
                writer.setBusinessAddressLane1(resultSet.getString(16));
                writer.setBusinessAddressLane2(resultSet.getString(17));
                writer.setBusinessCity(resultSet.getString(18));
                writer.setBusinessCountry(resultSet.getString(19));
                writer.setLandline(resultSet.getString(20));
                writer.setCurrentBalance(resultSet.getDouble(21));
                writer.setBio(resultSet.getString(22));
                writerList.add(writer);
            }
            return writerList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<PayoutAdminDTO> getPayoutList() throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM payout ORDER BY status DESC, requestedAt"
            );
            WriterService writerService = new WriterService(new WriterRepository(), new UserRepository());
            ResultSet resultSet = statement.executeQuery();
            List<PayoutAdminDTO> payoutList = new ArrayList<>();
            while (resultSet.next()) {
                Payout payout = new Payout(
                        resultSet.getInt("payoutId"),
                        resultSet.getInt("writerId"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("accountNumber"),
                        resultSet.getString("bank"),
                        resultSet.getString("branch"),
                        new java.util.Date(resultSet.getTimestamp("requestedAt").getTime()),
                        resultSet.getString("paymentSlipUrl"),
                        resultSet.getTimestamp("completedAt") != null?
                                new java.util.Date(resultSet.getTimestamp("completedAt").getTime()):
                                null
                        ,
                        PayoutStatus.valueOf(resultSet.getString("status"))
                );
                payoutList.add(
                        new PayoutAdminDTO(payout, writerService.getWriterById(payout.getWriterId()))
                );
            }
            return payoutList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void markPayoutCompleted(int payoutId, String paymentSlipUrl) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE payout SET paymentSlipUrl = ?, status = ?, completedAt = CURRENT_TIMESTAMP " +
                            "WHERE payoutId = ?"
            );
            statement.setString(1, paymentSlipUrl);
            statement.setString(2, PayoutStatus.COMPLETED.toString());
            statement.setInt(3, payoutId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
