package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereFormDTO;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Types.SubscriptionStatus;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Date;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(User user) throws NoSuchAlgorithmException, SQLException, IOException, ClassNotFoundException {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userRepository.createNewUser(user);
    }

    public User checkCredentials(User user) throws SQLException, IOException, ClassNotFoundException {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        User matchedUser = userRepository.findUserByUserName(user.getUserName());
        if (matchedUser != null && matchedUser.getPassword().equals(user.getPassword())) {
            return matchedUser;
        }
        return null;
    }

    public WriterApplicant registerWriterApplicant(WriterApplicant applicant) throws SQLException, IOException, ClassNotFoundException {
        applicant.setRequestedAt(new Date());
        applicant.setResponse("");
        applicant.setRespondedAt(null);
        return userRepository.registerWriterApplicant(applicant);
    }

    public PayhereFormDTO registerReader(Reader reader) throws SQLException, IOException, ClassNotFoundException {
        reader.setSubscriptionStatus(SubscriptionStatus.PENDING);
        reader = userRepository.registerReader(reader);
        return new PayhereFormDTO(
                reader.getFirstName(), reader.getLastName(), reader.getEmail(), reader.getPhoneNumber(),
                reader.getAddressLane1(), reader.getAddressLane2(), reader.getCity(), reader.getCountry()
        );
    }
}
