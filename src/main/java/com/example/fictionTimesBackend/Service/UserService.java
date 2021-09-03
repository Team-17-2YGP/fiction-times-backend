package com.example.fictionTimesBackend.Service;

import com.example.fictionTimesBackend.Model.User;
import com.example.fictionTimesBackend.Repository.UserRepository;
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
}
