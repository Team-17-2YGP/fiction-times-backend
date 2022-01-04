package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.*;
import com.fictiontimes.fictiontimesbackend.model.DTO.*;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

public class ReaderService {
    private final Logger logger = Logger.getLogger(ReaderService.class.getName());
    private UserRepository userRepository;
    private ReaderRepository readerRepository;
    private WriterRepository writerRepository;
    private StoryRepository storyRepository;

    public ReaderService(UserRepository userRepository, ReaderRepository readerRepository, WriterRepository writerRepository) {
        this.userRepository = userRepository;
        this.readerRepository = readerRepository;
        this.writerRepository = writerRepository;
    }

    public ReaderService(UserRepository userRepository, ReaderRepository readerRepository, WriterRepository writerRepository, StoryRepository storyRepository) {
        this.userRepository = userRepository;
        this.readerRepository = readerRepository;
        this.writerRepository = writerRepository;
        this.storyRepository = storyRepository;
    }

    public Reader getReaderById(int readerId) throws DatabaseOperationException {
        return readerRepository.findReaderById(readerId);
    }

    public void verifyReaderSubscription(PayhereNotifyDTO payhereNotifyDTO) throws DatabaseOperationException, IOException {
        User user = userRepository.findUserByEmail(payhereNotifyDTO.getCustom_1());
        readerRepository.verifyReaderSubscription(user.getUserId());
        readerRepository.saveReaderPaymentDetails(payhereNotifyDTO, user.getUserId());
        logger.info("Sending the email");
        EmailUtils.sendMail(
                user,
                "New Fiction Times Profile Email Verification",
                "To continue creating your new fiction times account please verify your email address.",
                CommonUtils.getDomain() + "/user/activate?token=" + AuthUtils.generateVerificationToken(user)
        );
    }

    public void saveReaderPaymentDetails(PayhereNotifyDTO payhereNotifyDTO) throws DatabaseOperationException {
        User user = userRepository.findUserByEmail(payhereNotifyDTO.getCustom_1());
        readerRepository.saveReaderPaymentDetails(payhereNotifyDTO, user.getUserId());
    }

    public List<User> getFollowingWritersList(int userId, int limit) throws DatabaseOperationException {
        return readerRepository.getFollowingWritersList(userId, limit);
    }

    public void followWriter(int readerId, int writerId) throws DatabaseOperationException {
        readerRepository.followWriter(readerId, writerId);
    }

    public void unFollowWriter(int readerId, int writerId) throws DatabaseOperationException {
        readerRepository.unfollowWriter(readerId, writerId);
    }

    public WriterDetailsDTO getWriterDetails(int readerId, int writerId) throws DatabaseOperationException {
        WriterDetailsDTO writerDetails = new WriterDetailsDTO();

        Writer writer = writerRepository.findWriterById(writerId);

        writerDetails.setUserId(writer.getUserId());
        writerDetails.setFirstName(writer.getFirstName());
        writerDetails.setLastName(writer.getLastName());
        writerDetails.setProfilePictureUrl(writer.getProfilePictureUrl());
        writerDetails.setBio(writer.getBio());
        writerDetails.setSubscribedNotifications(readerRepository.getNotificationStatus(readerId, writerId));
        writerDetails.setFollowing(readerRepository.getFollowingStatus(readerId, writerId));
        writerDetails.setFollowerCount(writerRepository.getFollowerCountById(writerId));
        writerDetails.setStoryCount(writerRepository.getStoryCountById(writerId));
        return writerDetails;
    }

    public void setNotificationStatus(int readerId, int writerId, boolean notificationStatus) throws DatabaseOperationException {
        readerRepository.setNotificationStatus(readerId, writerId, notificationStatus);
    }

    public void updateReaderProfileDetails(Reader readerDetails) throws DatabaseOperationException {
        userRepository.updateUserDetails(readerDetails);
    }

    public ReaderSearchDTO generalSearch(String keyword) throws DatabaseOperationException {
        return readerRepository.generalSearch(keyword);
    }

    public void likeUnlikeStory(int readerId, int storyId, boolean like) throws DatabaseOperationException {
        if(like) {
            readerRepository.likeStory(readerId, storyId);
        } else {
            readerRepository.unlikeStory(readerId, storyId);
        }
        storyRepository.updateStoryLikeCount(storyId, like);
    }

    public List<ReaderStoryDTO> getLikedStoriesList(int readerId, int limit) throws DatabaseOperationException {
        return storyRepository.getLikedStoriesList(readerId, limit);
    }

    public void addStoryReview(StoryReviewDTO storyReview) throws DatabaseOperationException {
        readerRepository.addStoryReview(storyReview);
    }

    public List<StoryReviewDTO> getStoryReviewList(int storyId, int limit, int offset) throws DatabaseOperationException {
        return storyRepository.getStoryReviewList(storyId, limit, offset);
    }

    public void addBookmark(int readerId, int episodeId) throws DatabaseOperationException {
        readerRepository.addBookmark(readerId, episodeId);
    }

    public void removeBookmark(int readerId, int episodeId) throws DatabaseOperationException {
        readerRepository.removeBookmark(readerId, episodeId);
    }

    public List<ReaderEpisodeDTO> getBookmarkList(int readerId) throws DatabaseOperationException {
        List<Episode> episodeList = storyRepository.getBookmarkList(readerId);
        List<ReaderEpisodeDTO> bookmarkList = new ArrayList<>();
        for (Episode episode : episodeList) {
            ReaderEpisodeDTO episodeDetails = new ReaderEpisodeDTO(
                    episode.getEpisodeId(),
                    episode.getStoryId(),
                    episode.getEpisodeNumber(),
                    episode.getTitle(),
                    episode.getDescription(),
                    episode.getReadCount(),
                    episode.getUploadedAt(),
                    null,
                    storyRepository.finishedReading(readerId, episode.getEpisodeId()),
                    true,
                    storyRepository.getStoryById(episode.getStoryId())
            );
            bookmarkList.add(episodeDetails);
        }
        return bookmarkList;
    }

    public void saveTimeData(TimeData timeData) throws DatabaseOperationException {
        timeData.setDuration(timeData.getClose().getTime() - timeData.getOpen().getTime());
        readerRepository.saveTimeData(timeData);
    }

    public void markAsRead(int readerId, int episodeId) throws DatabaseOperationException {
        readerRepository.markAsRead(readerId, episodeId);
    }

    public List<ReaderStoryDTO> getStoryListByGenre(int genreId, int limit) throws DatabaseOperationException {
        return storyRepository.getStoryListByGenre(genreId, limit);
    }
}
