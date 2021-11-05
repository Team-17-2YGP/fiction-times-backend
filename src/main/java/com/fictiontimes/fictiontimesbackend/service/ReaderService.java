package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereNotifyDTO;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class ReaderService {
    private UserRepository userRepository;
    private ReaderRepository readerRepository;
    private final Logger logger = Logger.getLogger(ReaderService.class.getName());

    public ReaderService(UserRepository userRepository, ReaderRepository readerRepository) {
        this.userRepository = userRepository;
        this.readerRepository = readerRepository;
    }

    public void verifyReaderSubscription(PayhereNotifyDTO payhereNotifyDTO) throws SQLException, IOException, ClassNotFoundException {
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

    public List<User> getFollowingWritersList(int userId, int limit) throws SQLException, IOException,
            ClassNotFoundException {
        return readerRepository.getFollowingWritersList(userId, limit);
    }

    public void followWriter(int readerId, int writerId) throws SQLException, IOException, ClassNotFoundException {
        readerRepository.followWriter(readerId, writerId);
    }

    public void unFollowWriter(int readerId, int writerId) throws SQLException, IOException, ClassNotFoundException {
        readerRepository.unfollowWriter(readerId, writerId);
    }

    public boolean getNotificationStatus(int readerId, int writerId) throws SQLException, IOException,
            ClassNotFoundException {
        return readerRepository.getNotificationStatus(readerId, writerId);
    }
    public void setNotificationStatus(int readerId, int writerId, boolean notificationStatus) throws SQLException, IOException, ClassNotFoundException {
        readerRepository.setNotificationStatus(readerId, writerId, notificationStatus);
    }
}
