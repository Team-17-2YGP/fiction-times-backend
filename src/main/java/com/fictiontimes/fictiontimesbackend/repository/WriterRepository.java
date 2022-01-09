package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.WriterStatsDTO;
import com.fictiontimes.fictiontimesbackend.model.Payout;
import com.fictiontimes.fictiontimesbackend.model.Types.PayoutStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.Writer;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
                writer.setBusinessAddressLane1(resultSet.getString(17));
                writer.setBusinessAddressLane2(resultSet.getString(18));
                writer.setBusinessCity(resultSet.getString(19));
                writer.setBusinessCountry(resultSet.getString(20));
                writer.setLandline(resultSet.getString(21));
                writer.setCurrentBalance(resultSet.getInt(22));
                writer.setBio(resultSet.getString(23));
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

    public Payout getPayoutById(int payoutId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM payout WHERE payoutId = ?"
            );
            statement.setInt(1, payoutId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Payout(
                        resultSet.getInt("payoutId"),
                        resultSet.getInt("writerId"),
                        resultSet.getDouble("amount"),
                        resultSet.getString("accountNumber"),
                        resultSet.getString("bank"),
                        resultSet.getString("branch"),
                        new Date(resultSet.getTimestamp("requestedAt").getTime()),
                        resultSet.getString("paymentSlipUrl"),
                        resultSet.getTimestamp("completedAt") != null?
                                new Date(resultSet.getTimestamp("completedAt").getTime()):
                                null
                        ,
                        PayoutStatus.valueOf(resultSet.getString("status"))
                );
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<Payout> getPayoutList(int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM payout WHERE writerId = ? ORDER BY status DESC, requestedAt"
            );
            statement.setInt(1, writerId);
            ResultSet resultSet = statement.executeQuery();
            List<Payout> payoutList = new ArrayList<>();
            while (resultSet.next()) {
                 payoutList.add(
                         new Payout(
                                 resultSet.getInt("payoutId"),
                                 resultSet.getInt("writerId"),
                                 resultSet.getDouble("amount"),
                                 resultSet.getString("accountNumber"),
                                 resultSet.getString("bank"),
                                 resultSet.getString("branch"),
                                 new Date(resultSet.getTimestamp("requestedAt").getTime()),
                                 resultSet.getString("paymentSlipUrl"),
                                 resultSet.getTimestamp("completedAt") != null?
                                         new Date(resultSet.getTimestamp("completedAt").getTime()):
                                         null
                                 ,
                                 PayoutStatus.valueOf(resultSet.getString("status"))
                         )
                 );
            }
            return payoutList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public long getMilliSecondsSinceTheLastPayout(int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT SUM(duration) as totalDuration from episode_time_data " +
                            "join episode e on e.episodeId = episode_time_data.episodeId " +
                            "join story s on e.storyId = s.storyId " +
                            "where closeTime > (SELECT MAX(requestedAt) as lastPayoutTime from payout where writerId = ?) " +
                            "and s.userId = ?"
            );
            statement.setInt(1, writerId);
            statement.setInt(2, writerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getLong("totalDuration");
            }
            return 0;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public WriterStatsDTO getStats(int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT (SELECT COUNT(*) FROM reader_following WHERE writerId=?) AS followersAllTime, " +
                            "(SELECT COUNT(*) FROM reader_following WHERE writerId=? " +
                            "   AND timestamp > ?) AS followersLastYear, " +
                            "(SELECT COUNT(*) FROM reader_following WHERE writerId=? " +
                            "   AND timestamp > ?) AS followers30Days, " +
                            "(SELECT COUNT(*) FROM reader_following WHERE writerId=? " +
                            "   AND timestamp > ?) AS followers7Days, " +
                            "(SELECT COUNT(*) FROM story_like sl INNER JOIN story s ON sl.storyId = s.storyId " +
                            "   WHERE s.userId=?) AS likesAllTime, " +
                            "(SELECT COUNT(*) FROM story_like sl INNER JOIN story s ON sl.storyId = s.storyId " +
                            "   WHERE s.userId=? AND sl.timestamp > ?) AS likesLastYear, " +
                            "(SELECT COUNT(*) FROM story_like sl INNER JOIN story s ON sl.storyId = s.storyId " +
                            "   WHERE s.userId=? AND sl.timestamp > ?) AS likes30Days, " +
                            "(SELECT COUNT(*) FROM story_like sl INNER JOIN story s ON sl.storyId = s.storyId " +
                            "   WHERE s.userId=? AND sl.timestamp > ?) AS likes7Days, " +
                            "(SELECT COUNT(*) FROM episode_read er INNER JOIN episode e INNER JOIN story s " +
                            "   ON er.episodeId=e.episodeId AND e.storyId =s.storyId " +
                            "   WHERE s.userId=?) AS readCountAllTime, " +
                            "(SELECT COUNT(*) FROM episode_read er INNER JOIN episode e INNER JOIN story s " +
                            "   ON er.episodeId=e.episodeId AND e.storyId =s.storyId " +
                            "   WHERE s.userId=? AND er.timestamp > ?) AS readCountLastYear, " +
                            "(SELECT COUNT(*) FROM episode_read er INNER JOIN episode e INNER JOIN story s " +
                            "   ON er.episodeId=e.episodeId AND e.storyId =s.storyId " +
                            "   WHERE s.userId=? AND er.timestamp > ?) AS readCount30Days, " +
                            "(SELECT COUNT(*) FROM episode_read er INNER JOIN episode e INNER JOIN story s " +
                            "   ON er.episodeId=e.episodeId AND e.storyId =s.storyId " +
                            "   WHERE s.userId=? AND er.timestamp > ?) AS readCount7Days "
            );
            LocalDateTime now = LocalDateTime.now();
            Timestamp lastYear = Timestamp.valueOf(LocalDateTime.of(now.getYear() -1, 1, 1, 0, 0, 0));
            Timestamp last30Days = Timestamp.valueOf(now.minusDays(30));
            Timestamp last7Days = Timestamp.valueOf(now.minusDays(7));

            statement.setInt(1, writerId);
            statement.setInt(2, writerId);
            statement.setTimestamp(3, lastYear);
            statement.setInt(4, writerId);
            statement.setTimestamp(5, last30Days);
            statement.setInt(6, writerId);
            statement.setTimestamp(7, last7Days);
            statement.setInt(8, writerId);
            statement.setInt(9, writerId);
            statement.setTimestamp(10, lastYear);
            statement.setInt(11, writerId);
            statement.setTimestamp(12, last30Days);
            statement.setInt(13, writerId);
            statement.setTimestamp(14, last7Days);
            statement.setInt(15, writerId);
            statement.setInt(16, writerId);
            statement.setTimestamp(17, lastYear);
            statement.setInt(18, writerId);
            statement.setTimestamp(19, last30Days);
            statement.setInt(20, writerId);
            statement.setTimestamp(21, last7Days);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new WriterStatsDTO(
                        resultSet.getInt("followersAllTime"),
                        resultSet.getInt("followersLastYear"),
                        resultSet.getInt("followers30Days"),
                        resultSet.getInt("followers7Days"),
                        resultSet.getInt("likesAllTime"),
                        resultSet.getInt("likesLastYear"),
                        resultSet.getInt("likes30Days"),
                        resultSet.getInt("likes7Days"),
                        resultSet.getInt("readCountAllTime"),
                        resultSet.getInt("readCountLastYear"),
                        resultSet.getInt("readCount30Days"),
                        resultSet.getInt("readCount7Days")
                );
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
