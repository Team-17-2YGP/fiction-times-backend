package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.*;
import com.fictiontimes.fictiontimesbackend.model.DTO.BlockReasonDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayoutAdminDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.AdminPlatformStatsDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayoutAdminDTO;
import com.fictiontimes.fictiontimesbackend.model.Types.*;
import com.fictiontimes.fictiontimesbackend.service.WriterService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
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
                writer.setBusinessAddressLane1(resultSet.getString(17));
                writer.setBusinessAddressLane2(resultSet.getString(18));
                writer.setBusinessCity(resultSet.getString(19));
                writer.setBusinessCountry(resultSet.getString(20));
                writer.setLandline(resultSet.getString(21));
                writer.setCurrentBalance(resultSet.getDouble(22));
                writer.setBio(resultSet.getString(23));
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
                writer.setBusinessAddressLane1(resultSet.getString(17));
                writer.setBusinessAddressLane2(resultSet.getString(18));
                writer.setBusinessCity(resultSet.getString(19));
                writer.setBusinessCountry(resultSet.getString(20));
                writer.setLandline(resultSet.getString(21));
                writer.setCurrentBalance(resultSet.getDouble(22));
                writer.setBio(resultSet.getString(23));
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
                writer.setBusinessAddressLane1(resultSet.getString(17));
                writer.setBusinessAddressLane2(resultSet.getString(18));
                writer.setBusinessCity(resultSet.getString(19));
                writer.setBusinessCountry(resultSet.getString(20));
                writer.setLandline(resultSet.getString(21));
                writer.setCurrentBalance(resultSet.getDouble(22));
                writer.setBio(resultSet.getString(23));
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
  
  public List<SubscriptionPayment> getSubscriptionPaymentList(int limit, int offset) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM subscription_payment " +
                            "WHERE status=? OR status=? " +
                            "ORDER BY timestamp DESC " +
                            "LIMIT ? " +
                            "OFFSET ? "
            );
            statement.setString(1, PayhereMessageType.RECURRING_INSTALLMENT_SUCCESS.toString());
            statement.setString(2, PayhereMessageType.RECURRING_INSTALLMENT_FAILED.toString());
            statement.setInt(3, limit);
            statement.setInt(4, offset);
            ResultSet resultSet = statement.executeQuery();

            List<SubscriptionPayment> paymentList = new ArrayList<>();
            while (resultSet.next()) {
                paymentList.add(new SubscriptionPayment(
                        resultSet.getInt("subscriptionPaymentId"),
                        resultSet.getInt("userId"),
                        resultSet.getString("paymentId"),
                        resultSet.getString("subscriptionId"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("currency"),
                        resultSet.getString("paymentMethod"),
                        PayhereMessageType.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("nextPaymentDate"),
                        resultSet.getInt("noOfPaymentsReceived"),
                        resultSet.getTimestamp("timestamp")
                ));
            }
            return paymentList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
  
  public List<SubscriptionPayment> searchSubscriptionPayments(String query) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM subscription_payment sp INNER JOIN user u ON sp.userId = u.userId " +
                            "WHERE (sp.status=? OR sp.status=?) AND " +
                            "(u.userId LIKE ? OR u.firstName LIKE ? OR u.lastName LIKE ? " +
                            "OR sp.subscriptionPaymentId LIKE ?) " +
                            "ORDER BY sp.timestamp DESC "
            );
            query = "%" + query + "%";
            statement.setString(1, PayhereMessageType.RECURRING_INSTALLMENT_SUCCESS.toString());
            statement.setString(2, PayhereMessageType.RECURRING_INSTALLMENT_FAILED.toString());
            statement.setString(3, query);
            statement.setString(4, query);
            statement.setString(5, query);
            statement.setString(6, query);
            ResultSet resultSet = statement.executeQuery();

            List<SubscriptionPayment> paymentList = new ArrayList<>();
            while (resultSet.next()) {
                paymentList.add(new SubscriptionPayment(
                        resultSet.getInt("subscriptionPaymentId"),
                        resultSet.getInt("userId"),
                        resultSet.getString("paymentId"),
                        resultSet.getString("subscriptionId"),
                        resultSet.getFloat("amount"),
                        resultSet.getString("currency"),
                        resultSet.getString("paymentMethod"),
                        PayhereMessageType.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("nextPaymentDate"),
                        resultSet.getInt("noOfPaymentsReceived"),
                        resultSet.getTimestamp(11)
                ));
            }
            return paymentList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Reader> getReadersList(int limit) throws DatabaseOperationException {
        try {
            List<Reader> readersList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN reader r on u.userId = r.userId " +
                            "WHERE u.userType = ? ORDER BY u.userId DESC " +
                            "LIMIT ?"
            );
            statement.setString(1, UserType.READER.toString());
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Reader reader = new Reader();
                reader.setUserId(resultSet.getInt(1));
                reader.setUserName(resultSet.getString(2));
                reader.setFirstName(resultSet.getString(3));
                reader.setLastName(resultSet.getString(4));
                reader.setEmail(resultSet.getString(6));
                reader.setAddressLane1(resultSet.getString(7));
                reader.setAddressLane2(resultSet.getString(8));
                reader.setCity(resultSet.getString(9));
                reader.setCountry(resultSet.getString(10));
                reader.setPhoneNumber(resultSet.getString(11));
                reader.setProfilePictureUrl(resultSet.getString(12));
                reader.setUserStatus(UserStatus.valueOf(resultSet.getString(14)));
                reader.setSubscriptionStatus(SubscriptionStatus.valueOf(resultSet.getString(17)));
                readersList.add(reader);
            }
            return readersList;
           } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
  
    public List<Reader> searchReadersByName(String userName) throws DatabaseOperationException {
        try {
            List<Reader> readersList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN reader r on u.userId = r.userId " +
                            "WHERE u.userType = ? AND u.firstName LIKE ? OR u.lastName LIKE ? OR u.userName LIKE ? ORDER BY u.userId DESC"
            );
            statement.setString(1, UserType.READER.toString());
            statement.setString(2, "%"+userName+"%");
            statement.setString(3, "%"+userName+"%");
            statement.setString(4,"%"+userName+"%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Reader reader = new Reader();
                reader.setUserId(resultSet.getInt(1));
                reader.setUserName(resultSet.getString(2));
                reader.setFirstName(resultSet.getString(3));
                reader.setLastName(resultSet.getString(4));
                reader.setEmail(resultSet.getString(6));
                reader.setAddressLane1(resultSet.getString(7));
                reader.setAddressLane2(resultSet.getString(8));
                reader.setCity(resultSet.getString(9));
                reader.setCountry(resultSet.getString(10));
                reader.setPhoneNumber(resultSet.getString(11));
                reader.setProfilePictureUrl(resultSet.getString(12));
                reader.setUserStatus(UserStatus.valueOf(resultSet.getString(14)));
                reader.setSubscriptionStatus(SubscriptionStatus.valueOf(resultSet.getString(17)));
                readersList.add(reader);
            }
            return readersList;
           } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
  
    public List<Reader> searchReadersById(int userId) throws DatabaseOperationException {
        try {
            List<Reader> readersList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN reader r on u.userId = r.userId " +
                            "WHERE u.userType = ? AND u.userId LIKE ? ORDER BY u.userId DESC"
            );
            statement.setString(1, UserType.READER.toString());
            statement.setString(2, "%"+userId+"%");

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                Reader reader = new Reader();
                reader.setUserId(resultSet.getInt(1));
                reader.setUserName(resultSet.getString(2));
                reader.setFirstName(resultSet.getString(3));
                reader.setLastName(resultSet.getString(4));
                reader.setEmail(resultSet.getString(6));
                reader.setAddressLane1(resultSet.getString(7));
                reader.setAddressLane2(resultSet.getString(8));
                reader.setCity(resultSet.getString(9));
                reader.setCountry(resultSet.getString(10));
                reader.setPhoneNumber(resultSet.getString(11));
                reader.setProfilePictureUrl(resultSet.getString(12));
                reader.setUserStatus(UserStatus.valueOf(resultSet.getString(14)));
                reader.setSubscriptionStatus(SubscriptionStatus.valueOf(resultSet.getString(17)));
                readersList.add(reader);
            }
            return readersList;
           } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
  
    public AdminPlatformStatsDTO getPlatformStats() throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT (SELECT COUNT(*) FROM user WHERE userType='READER') as totalReaderCount, " +
                            "(SELECT COUNT(*) FROM user WHERE userType='WRITER') as totalWriterCount, " +
                            "(SELECT COUNT(*) FROM user WHERE userType='ADMIN') as totalAdminCount, " +
                            "(SELECT COUNT(*) FROM user WHERE userType='WRITER_APPLICANT') as totalApplicantCount, " +
                            "(SELECT COUNT(*) FROM user u WHERE u.userType='WRITER' " +
                            "   AND u.timestamp > ?) as writerRegistrations30Days, " +
                            "(SELECT COUNT(*) FROM user u WHERE u.userType='READER' " +
                            "   AND u.timestamp > ?) as readerRegistrations30Days, " +
                            "(SELECT SUM(amount) FROM subscription_payment WHERE status='RECURRING_INSTALLMENT_SUCCESS') " +
                            "   as totalSubscriptionPaymentsAllTime, " +
                            "(SELECT SUM(amount) FROM subscription_payment WHERE status='RECURRING_INSTALLMENT_SUCCESS' " +
                            "   AND timestamp > ?) as totalSubscriptionPaymentsThisYear, " +
                            "(SELECT SUM(amount) FROM subscription_payment WHERE status='RECURRING_INSTALLMENT_SUCCESS' " +
                            "   AND timestamp > ?) as totalSubscriptionPayments30Days, " +
                            "(SELECT SUM(amount) FROM payout) as totalPayoutsAllTime, " +
                            "(SELECT SUM(amount) FROM payout WHERE completedAt > ?) as totalPayoutsThisYear, " +
                            "(SELECT SUM(amount) FROM payout WHERE completedAt > ?) as totalPayouts30Days "
            );
            LocalDateTime now = LocalDateTime.now();
            Timestamp thisYear = Timestamp.valueOf(LocalDateTime.of(now.getYear(), 1, 1, 0, 0, 0));
            Timestamp last30Days = Timestamp.valueOf(now.minusDays(30));
            statement.setTimestamp(1, last30Days);
            statement.setTimestamp(2, last30Days);
            statement.setTimestamp(3, thisYear);
            statement.setTimestamp(4, last30Days);
            statement.setTimestamp(5, thisYear);
            statement.setTimestamp(6, last30Days);

            ResultSet resultSet = statement.executeQuery();
            AdminPlatformStatsDTO platformStats = new AdminPlatformStatsDTO();
            if(resultSet.next()) {
                platformStats.setTotalReaderCount(resultSet.getInt("totalReaderCount"));
                platformStats.setTotalWriterCount(resultSet.getInt("totalWriterCount"));
                platformStats.setTotalAdminCount(resultSet.getInt("totalAdminCount"));
                platformStats.setTotalApplicantCount(resultSet.getInt("totalApplicantCount"));
                platformStats.setWriterRegistrations30Days(resultSet.getInt("writerRegistrations30Days"));
                platformStats.setReaderRegistrations30Days(resultSet.getInt("readerRegistrations30Days"));
                platformStats.setTotalSubscriptionPaymentsAllTime(resultSet.getDouble("totalSubscriptionPaymentsAllTime"));
                platformStats.setTotalSubscriptionPaymentsThisYear(resultSet.getDouble("totalSubscriptionPaymentsThisYear"));
                platformStats.setTotalSubscriptionPayments30Days(resultSet.getDouble("totalSubscriptionPayments30Days"));
                platformStats.setTotalPayoutsAllTime(resultSet.getDouble("totalPayoutsAllTime"));
                platformStats.setTotalPayoutsThisYear(resultSet.getDouble("totalPayoutsThisYear"));
                platformStats.setTotalPayouts30Days(resultSet.getDouble("totalPayouts30Days"));
            }
            return platformStats;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void setReasonToBlockUser(BlockReasonDTO reason) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO user_block_reason VALUES (?, ?)"
            );
            statement.setInt(1, reason.getUser().getUserId());
            statement.setString(2, reason.getReason());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public String getReasonToBlockUser(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT reason FROM user_block_reason WHERE userId = ?"
            );
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String reason = resultSet.getString("reason");
                return reason;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void deleteReasonToBlockUser(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM user_block_reason WHERE userId = ?"
            );
            statement.setInt(1, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public Reader getReaderById(int readerId) throws DatabaseOperationException {
        try {
            Reader reader = new Reader();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN reader r on u.userId = r.userId WHERE r.userId = ?"
            );
            statement.setInt(1, readerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reader.setUserId(resultSet.getInt("userId"));
                reader.setUserName(resultSet.getString("userName"));
                reader.setFirstName(resultSet.getString("firstName"));
                reader.setLastName(resultSet.getString("lastName"));
                reader.setEmail(resultSet.getString("email"));
                reader.setAddressLane1(resultSet.getString("addressLane1"));
                reader.setAddressLane2(resultSet.getString("addressLane2"));
                reader.setCity(resultSet.getString("city"));
                reader.setCountry(resultSet.getString("country"));
                reader.setPhoneNumber(resultSet.getString("phoneNumber"));
                reader.setProfilePictureUrl(resultSet.getString("profilePictureUrl"));
                reader.setUserStatus(UserStatus.valueOf(resultSet.getString("userStatus")));
                reader.setSubscriptionStatus(SubscriptionStatus.valueOf(resultSet.getString("subscriptionStatus")));
                return reader;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public Writer getWriterById(int writerId) throws DatabaseOperationException {
        try {
            Writer writer = new Writer();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN writer w on u.userId = w.userId WHERE u.userId = ?"
            );
            statement.setInt(1, writerId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
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
                writer.setBusinessAddressLane1(resultSet.getString(17));
                writer.setBusinessAddressLane2(resultSet.getString(18));
                writer.setBusinessCity(resultSet.getString(19));
                writer.setBusinessCountry(resultSet.getString(20));
                writer.setLandline(resultSet.getString(21));
                writer.setCurrentBalance(resultSet.getDouble(22));
                writer.setBio(resultSet.getString(23));
                return writer;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
