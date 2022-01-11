package com.fictiontimes.fictiontimesbackend.repository;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.*;
import com.fictiontimes.fictiontimesbackend.model.DTO.*;
import com.fictiontimes.fictiontimesbackend.model.Types.*;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.google.gson.reflect.TypeToken;
import me.xdrop.fuzzywuzzy.FuzzySearch;
import me.xdrop.fuzzywuzzy.model.BoundExtractedResult;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReaderRepository {

    private PreparedStatement statement;

    public Reader findReaderById(int readerId) throws DatabaseOperationException {
        try {
            Reader reader = new Reader();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM user u INNER JOIN reader r ON u.userId=r.userId WHERE u.userId=?"
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
                reader.setUserType(UserType.valueOf(resultSet.getString("userType")));
                reader.setUserStatus(UserStatus.valueOf(resultSet.getString("userStatus")));
                reader.setSubscriptionStatus(SubscriptionStatus.valueOf(resultSet.getString("subscriptionStatus")));
                reader.setInitialized(resultSet.getBoolean("isInitialized"));
                return reader;
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void verifyReaderSubscription(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement("UPDATE reader SET subscriptionStatus = ? WHERE userId = ?");
            statement.setString(1, SubscriptionStatus.VERIFIED.toString());
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void saveReaderPaymentDetails(PayhereNotifyDTO payhereNotifyDTO, int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO subscription_payment (userId, paymentId, subscriptionId, amount, currency, " +
                            "paymentMethod, status, nextPaymentDate, noOfPaymentsReceived, timestamp) " +
                            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setInt(1, userId);
            statement.setString(2, payhereNotifyDTO.getPayment_id());
            statement.setString(3, payhereNotifyDTO.getSubscription_id());
            statement.setDouble(4, payhereNotifyDTO.getPayhere_amount());
            statement.setString(5, payhereNotifyDTO.getPayhere_currency());
            statement.setString(6, payhereNotifyDTO.getMethod());
            statement.setString(7, payhereNotifyDTO.getMessage_type().toString());
            statement.setObject(8, new Timestamp(payhereNotifyDTO.getItem_rec_date_next().getTime()));
            statement.setInt(9, payhereNotifyDTO.getItem_rec_install_paid());
            statement.setObject(10, new Timestamp(new Date().getTime()));
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<User> getFollowingWritersList(int userId, int limit) throws DatabaseOperationException {
        try {
            List<User> writerList = new ArrayList<>();
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT u.userId, u.firstName, u.lastName, u.profilePictureUrl " +
                            "FROM user u " +
                            "INNER JOIN reader_following rf on u.userId = rf.writerId " +
                            "WHERE rf.readerId = ? " +
                            "ORDER BY rf.timestamp DESC " +
                            "LIMIT ?"
            );
            statement.setInt(1, userId);
            statement.setInt(2, limit);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {

                User writer = new User();
                writer.setUserId(resultSet.getInt("userId"));
                writer.setFirstName(resultSet.getString("firstName"));
                writer.setLastName(resultSet.getString("lastName"));
                writer.setProfilePictureUrl(resultSet.getString("profilePictureUrl"));

                writerList.add(writer);
            }
            return writerList;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void followWriter(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO reader_following VALUES (?, ?, ?, ?)"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);
            statement.setBoolean(3, false);
            statement.setObject(4, new Timestamp(new Date().getTime()));

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void unfollowWriter(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM reader_following WHERE readerId=? AND writerId=?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public boolean getFollowingStatus(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT writerId FROM reader_following WHERE readerId=? AND writerId=?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);

            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public boolean getNotificationStatus(int readerId, int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT notification FROM reader_following WHERE readerId=? AND writerId=?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, writerId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("notification");
            }
            return false;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void setNotificationStatus(int readerId, int writerId, boolean notificationStatus) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE reader_following SET notification=? WHERE readerId=? AND writerId=?"
            );
            statement.setBoolean(1, notificationStatus);
            statement.setInt(2, readerId);
            statement.setInt(3, writerId);

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public ReaderSearchDTO generalSearch(String keyword) throws DatabaseOperationException {
        try {
            ReaderSearchDTO readerSearchDTO = new ReaderSearchDTO();
            // search matching stories
            statement = DBConnection.getConnection().prepareStatement("SELECT * FROM story");
            ResultSet resultSet = statement.executeQuery();
            List<Story> storyList = new ArrayList<>();
            Type tagListType = new TypeToken<ArrayList<String>>() {}.getType();
            StoryRepository storyRepository = new StoryRepository();
            while (resultSet.next()) {
                storyList.add(new Story(
                        resultSet.getInt("storyId"),
                        resultSet.getInt("userId"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getInt("likeCount"),
                        resultSet.getString("coverArtUrl"),
                        null,
                        StoryStatus.valueOf(resultSet.getString("status")),
                        resultSet.getTimestamp("releasedDate"),
                        CommonUtils.getGson().fromJson(resultSet.getString("tags"), tagListType),
                        storyRepository.getStoryGenreList(resultSet.getInt("storyId"))
                ));
            }
            List<BoundExtractedResult<Story>> fuseStory = FuzzySearch.extractSorted(keyword, storyList, Story::getTitle, 40);
            for (BoundExtractedResult<Story> result: fuseStory) {
                readerSearchDTO.add(new ReaderStoryDTO(result.getReferent()));
            }
            // search matching writers
            statement = DBConnection.getConnection().prepareStatement("SELECT * FROM user WHERE user.userType = 'WRITER'");
            resultSet = statement.executeQuery();
            List<User> writerList = new ArrayList<>();
            while (resultSet.next()) {
                User writer = new User();
                writer.setUserId(resultSet.getInt("userId"));
                writer.setFirstName(resultSet.getString("firstName"));
                writer.setLastName(resultSet.getString("lastName"));
                writer.setProfilePictureUrl(resultSet.getString("profilePictureUrl"));
                writerList.add(writer);
            }
            List<BoundExtractedResult<User>> fuseWriter = FuzzySearch.extractSorted(keyword, writerList, x -> x.getFirstName() + " " + x.getLastName(), 40);
            for (BoundExtractedResult<User> result: fuseWriter) {
                readerSearchDTO.add(result.getReferent());
            }
            // search matching episodes
            statement = DBConnection.getConnection().prepareStatement("SELECT " +
                    "episodeId, e.title as title, e.description as description, s.storyId as storyId," +
                    "s.title as storyTitle, coverArtUrl " +
                    "FROM episode e JOIN story s ON e.storyId=s.storyId");
            resultSet = statement.executeQuery();
            List<SearchEpisodeDTO> episodeList = new ArrayList<>();
            while (resultSet.next()) {
                SearchEpisodeDTO episode = new SearchEpisodeDTO();
                episode.setEpisodeId(resultSet.getInt("episodeId"));
                episode.setTitle(resultSet.getString("title"));
                episode.setDescription(resultSet.getString("description"));
                episode.setStoryId(resultSet.getInt("storyId"));
                episode.setStoryTitle(resultSet.getString("storyTitle"));
                episode.setCoverArtUrl(resultSet.getString("coverArtUrl"));
                episodeList.add(episode);
            }
            List<BoundExtractedResult<SearchEpisodeDTO>> fuseEpisode = FuzzySearch.extractSorted(keyword, episodeList, Episode::getTitle, 40);
            for (BoundExtractedResult<SearchEpisodeDTO> result: fuseEpisode) {
                readerSearchDTO.add(result.getReferent());
            }
            // search matching genres
            statement = DBConnection.getConnection().prepareStatement("SELECT * FROM genre");
            resultSet = statement.executeQuery();
            List<Genre> genreList = new ArrayList<>();
            while (resultSet.next()) {
                genreList.add(new Genre(
                        resultSet.getInt("genreId"),
                        resultSet.getString("genreName")
                ));
            }
            List<BoundExtractedResult<Genre>> fuseGenre = FuzzySearch.extractSorted(keyword, genreList, Genre::getGenreName, 40);
            for (BoundExtractedResult<Genre> result: fuseGenre) {
                readerSearchDTO.add(result.getReferent());
            }
            return readerSearchDTO;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void likeStory(int readerId, int storyId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO story_like VALUES (?, ?, ?)"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, storyId);
            statement.setObject(3, new Timestamp(new Date().getTime()));

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void unlikeStory(int readerId, int storyId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM story_like WHERE readerId=? AND storyId=?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2, storyId);

            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void addStoryReview(StoryReviewDTO storyReview) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO story_review VALUES (?, ?, ?, ?, ?)"
            );
            statement.setInt(1, storyReview.getStoryId());
            statement.setInt(2, storyReview.getReader().getUserId());
            statement.setInt(3, storyReview.getRating());
            statement.setString(4, storyReview.getReview());
            statement.setObject(5, new Timestamp(new Date().getTime()));
            statement.executeUpdate();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void addBookmark(int readerId, int episodeId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO bookmark VALUES (?,?,?)"
            );
            statement.setInt(1, readerId);
            statement.setInt(2,episodeId);
            statement.setTimestamp(3, new Timestamp(new Date().getTime()));
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void removeBookmark(int readerId, int episodeId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "DELETE FROM bookmark WHERE readerId =? AND episodeId =?"
            );
            statement.setInt(1, readerId);
            statement.setInt(2,episodeId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void saveTimeData(TimeData timeData) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO episode_time_data VALUES(?, ?, ?, ?, ?)"
            );
            statement.setInt(1, timeData.getEpisodeId());
            statement.setInt(2, timeData.getReaderId());
            statement.setTimestamp(3, new Timestamp(timeData.getOpen().getTime()));
            statement.setTimestamp(4, new Timestamp(timeData.getClose().getTime()));
            statement.setLong(5, timeData.getDuration());
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void markAsRead(int readerId, int episodeId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "INSERT INTO episode_read VALUES (?,?,?)"
            );
            statement.setInt(1, readerId);
            statement.setInt(2,episodeId);
            statement.setTimestamp(3, new Timestamp(new Date().getTime()));
            statement.execute();

            statement = DBConnection.getConnection().prepareStatement(
                    "UPDATE episode SET readCount=readCount+1 WHERE episodeId=?"
            );
            statement.setInt(1,episodeId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public List<ReaderStoryDTO> getStoryListByWriter(int writerId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "SELECT * FROM story WHERE userId = ?"
            );
            statement.setInt(1, writerId);
            ResultSet resultSet = statement.executeQuery();
            return getReaderStoryDTO(resultSet);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public ReaderHomeDTO getReaderRecommendations(int userId) throws DatabaseOperationException {
        return new ReaderHomeDTO(
                getRecommendedOnLiked(userId),
                getRecommendedOnRead(userId),
                getRecommendedOnGenre(userId)
        );
    }

    private List<ReaderStoryDTO> getRecommendedOnLiked(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "select distinct story.* from story inner join story_genre sg on story.storyId = sg.storyId " +
                            "where genreId in (select distinct genreId from story_like inner join story_genre sg " +
                            "on story_like.storyId = sg.storyId where readerId = ?) " +
                            "and story.storyId not in (select distinct story.storyId from story inner join episode e " +
                            "on story.storyId = e.storyId inner join episode_read er on e.episodeId = er.episodeId " +
                            "where readerId = ? " +
                            "union " +
                            "select distinct storyId from story_like where readerId = ?) " +
                            "order by rand() limit 5"
            );
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, userId);
            return getReaderStoryDTO(statement.executeQuery());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    private List<ReaderStoryDTO> getRecommendedOnRead(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "select distinct story.* from story inner join story_genre sg on story.storyId = sg.storyId " +
                            "where genreId in (select distinct genreId from episode_read inner join episode e on " +
                            "episode_read.episodeId = e.episodeId inner join story_genre sg on " +
                            "e.storyId = sg.storyId where readerId = ?)" +
                            "and story.storyId not in (select distinct story.storyId from story inner join episode e " +
                            "on story.storyId = e.storyId inner join episode_read er on e.episodeId = er.episodeId " +
                            "where readerId = ? " +
                            "union " +
                            "select distinct storyId from story_like where readerId = ?) order by rand() limit 5"
            );
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, userId);
            return getReaderStoryDTO(statement.executeQuery());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    private List<ReaderStoryDTO> getRecommendedOnGenre(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "select distinct story.* from story inner join story_genre sg on story.storyId = sg.storyId " +
                            "where genreId in (select distinct genreId from genre_like where readerId = ?) " +
                            "and story.storyId not in (select distinct story.storyId from story inner join episode e " +
                            "on story.storyId = e.storyId inner join episode_read er on e.episodeId = er.episodeId " +
                            "where readerId = ? " +
                            "union " +
                            "select distinct storyId from story_like where readerId = ?) " +
                            "order by rand() limit 5"
            );
            statement.setInt(1, userId);
            statement.setInt(2, userId);
            statement.setInt(3, userId);
            return getReaderStoryDTO(statement.executeQuery());
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    private List<ReaderStoryDTO> getReaderStoryDTO(ResultSet resultSet) throws SQLException, DatabaseOperationException {
        List<ReaderStoryDTO> storyList = new ArrayList<>();
        Type tagListType = new TypeToken<ArrayList<String>>() {}.getType();
        StoryRepository storyRepository = new StoryRepository();
        while (resultSet.next()) {
            storyList.add(new ReaderStoryDTO(new Story(
                    resultSet.getInt("storyId"),
                    resultSet.getInt("userId"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getInt("likeCount"),
                    resultSet.getString("coverArtUrl"),
                    null,
                    StoryStatus.valueOf(resultSet.getString("status")),
                    resultSet.getTimestamp("releasedDate"),
                    CommonUtils.getGson().fromJson(resultSet.getString("tags"), tagListType),
                    storyRepository.getStoryGenreList(resultSet.getInt("storyId"))
            )));
        }
        return storyList;
    }

    private List<ReaderStoryDTO> getReaderStoryDTOWithRating(ResultSet resultSet) throws SQLException, DatabaseOperationException {
        List<ReaderStoryDTO> storyList = new ArrayList<>();
        Type tagListType = new TypeToken<ArrayList<String>>() {}.getType();
        StoryRepository storyRepository = new StoryRepository();
        while (resultSet.next()) {
            storyList.add(new ReaderStoryDTO(new Story(
                    resultSet.getInt("storyId"),
                    resultSet.getInt("userId"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getInt("likeCount"),
                    resultSet.getString("coverArtUrl"),
                    null,
                    StoryStatus.valueOf(resultSet.getString("status")),
                    resultSet.getTimestamp("releasedDate"),
                    CommonUtils.getGson().fromJson(resultSet.getString("tags"), tagListType),
                    storyRepository.getStoryGenreList(resultSet.getInt("storyId")),
                    0,
                    resultSet.getFloat("rating")
            )));
        }
        return storyList;
    }

    public void likeGenre(int userId, List<Integer> genreIdList) throws DatabaseOperationException {
        try {
            for (Integer genreId: genreIdList) {
                statement = DBConnection.getConnection().prepareStatement(
                        "INSERT INTO genre_like(genreId, readerId) values (?, ?)"
                );
                statement.setInt(1, genreId);
                statement.setInt(2, userId);
                statement.execute();
            }
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public GenreDetailsDTO getGenreDetails(int userId, int genreId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "select * from genre_like where genreId = ? and readerId = ?"
            );
            statement.setInt(1, genreId);
            statement.setInt(2, userId);
            ResultSet resultSet = statement.executeQuery();
            boolean isLiked = resultSet.next();
            statement = DBConnection.getConnection().prepareStatement(
                    "select * from genre where genreId = ?"
            );
            statement.setInt(1, genreId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new GenreDetailsDTO(
                        genreId,
                        isLiked,
                        resultSet.getString("genreName")
                );
            }
            return null;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void unlikeGenre(int userId, int genreId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "delete from genre_like where genreId = ? and readerId = ?"
            );
            statement.setInt(1, genreId);
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public void initReaderProfile(int userId) throws DatabaseOperationException {
        try {
            statement = DBConnection.getConnection().prepareStatement(
                    "update reader set isInitialized = ? where userId = ?"
            );
            statement.setBoolean(1, true);
            statement.setInt(2, userId);
            statement.execute();
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public ReaderStats getReaderStats(int readerId) throws DatabaseOperationException {
        try {
            ReaderStats readerStats = new ReaderStats();
            statement = DBConnection.getConnection().prepareStatement(
                    "select sum(duration) as time from episode_time_data where readerId = ?"
            );
            statement.setInt(1, readerId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                readerStats.setTime(resultSet.getInt("time"));
            }
            statement = DBConnection.getConnection().prepareStatement(
                    "select count(*) as writers from reader_following where readerId = ?"
            );
            statement.setInt(1, readerId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                readerStats.setWriterCount(resultSet.getInt("writers"));
            }
            statement = DBConnection.getConnection().prepareStatement(
                    "select count(episodeId) as episodes from episode_read where readerId = ?"
            );
            statement.setInt(1, readerId);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                readerStats.setEpisodeCount(resultSet.getInt("episodes"));
            }
            return readerStats;
        } catch (SQLException | IOException | ClassNotFoundException e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }
}
