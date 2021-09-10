package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
}
