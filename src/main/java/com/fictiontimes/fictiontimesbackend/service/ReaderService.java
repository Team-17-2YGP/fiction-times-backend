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
}
