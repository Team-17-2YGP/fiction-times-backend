package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Payout;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.Writer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;

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

    public void updateWriterDetails(Writer writer) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE writer " +
                            "SET addressLane1 = ?," +
                            "    addressLane2 = ?," +
                            "    city = ?," +
                            "    country = ?," +
                            "    landline = ?," +
                            "    bio = ?" +
                            "WHERE userId = ?"
            );
            statement.setString(1, Objects.requireNonNull(writer.getBusinessAddressLane2()));
            statement.setString(2, Objects.requireNonNull(writer.getBusinessAddressLane2()));
            statement.setString(3, Objects.requireNonNull(writer.getBusinessCity()));
            statement.setString(4, Objects.requireNonNull(writer.getBusinessCountry()));
            statement.setString(5, Objects.requireNonNull(writer.getLandline()));
            statement.setString(6, Objects.requireNonNull(writer.getBio()));
            statement.setInt(7, writer.getUserId());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public Payout requestPayout(Payout payout) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO payout (writerId, amount, accountNumber, bank, branch, status, requestedAt) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?)",
                    statement.RETURN_GENERATED_KEYS
            );
            statement.setInt(1, payout.getWriterId());
            statement.setDouble(2, payout.getAmount());
            statement.setString(3, payout.getAccountNumber());
            statement.setString(4, payout.getBank());
            statement.setString(5, payout.getBranch());
            statement.setString(6, payout.getStatus().toString());
            statement.setTimestamp(7, new Timestamp(payout.getRequestedAt().getTime()));
            statement.execute();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                payout.setPayoutId(resultSet.getInt(1));
            }
            return payout;
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new DatabaseOperationException(e.getMessage());
        }

    }

    public void resetWriterBalance(int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE writer SET currentBalance = 0 WHERE writer.userId = ?"
            );
            statement.setInt(1, writerId);
            statement.executeUpdate();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
