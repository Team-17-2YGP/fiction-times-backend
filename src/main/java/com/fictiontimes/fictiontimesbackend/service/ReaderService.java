package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereNotifyDTO;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ReaderService {
    private UserRepository userRepository;
    private ReaderRepository readerRepository;

    public ReaderService(UserRepository userRepository, ReaderRepository readerRepository) {
        this.userRepository = userRepository;
        this.readerRepository = readerRepository;
    }

    public void verifyReaderSubscription(PayhereNotifyDTO payhereNotifyDTO) throws SQLException, IOException, ClassNotFoundException {
        User user = userRepository.findUserByEmail(payhereNotifyDTO.getCustom_1());
        readerRepository.verifyReaderSubscription(user.getUserId());
        // TODO: Send the email to the reader
    }
}
