package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;

public class WriterService {
    WriterRepository writerRepository;
    UserRepository userRepository;

    public WriterService(WriterRepository writerRepository, UserRepository userRepository) {
        this.writerRepository = writerRepository;
        this.userRepository = userRepository;
    }

    public Writer getWriterById(int writerId) throws DatabaseOperationException {
        return writerRepository.findWriterById(writerId);
    }

    public void updateWriterProfileDetails(Writer writerDetails) throws DatabaseOperationException {
        userRepository.updateUserDetails(writerDetails);
        writerRepository.updateWriterDetails(writerDetails);
    }
}
