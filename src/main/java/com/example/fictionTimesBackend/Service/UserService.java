package com.example.fictionTimesBackend.Service;

import com.example.fictionTimesBackend.Model.User;
import com.example.fictionTimesBackend.Repository.UserRepository;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(User user) throws NoSuchAlgorithmException, SQLException, IOException, ClassNotFoundException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(user.getPassword().getBytes());
        byte[] digest = messageDigest.digest();
        String password = DatatypeConverter.printHexBinary(digest);
        user.setPassword(password);
        return userRepository.createNewUser(user);
    }
}
