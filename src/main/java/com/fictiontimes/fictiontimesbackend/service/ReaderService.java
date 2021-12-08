package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereNotifyDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderSearchDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.WriterDetailsDTO;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class ReaderService {
    private final Logger logger = Logger.getLogger(ReaderService.class.getName());
    private UserRepository userRepository;
    private ReaderRepository readerRepository;
    private WriterRepository writerRepository;

    public ReaderService(UserRepository userRepository, ReaderRepository readerRepository, WriterRepository writerRepository) {
        this.userRepository = userRepository;
        this.readerRepository = readerRepository;
        this.writerRepository = writerRepository;
    }

    public Reader getReaderById(int readerId) throws DatabaseOperationException {
        return readerRepository.findReaderById(readerId);
    }

    public void verifyReaderSubscription(PayhereNotifyDTO payhereNotifyDTO) throws DatabaseOperationException, IOException {
        User user = userRepository.findUserByEmail(payhereNotifyDTO.getCustom_1());
        readerRepository.verifyReaderSubscription(user.getUserId());
        logger.info("Sending the email");
        EmailUtils.sendMail(
                user,
                "New Fiction Times Profile Email Verification",
                "To continue creating your new fiction times account please verify your email address.",
                CommonUtils.getDomain() + "/user/activate?token=" + AuthUtils.generateVerificationToken(user)
        );
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
}
