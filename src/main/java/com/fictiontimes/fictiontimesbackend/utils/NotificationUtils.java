package com.fictiontimes.fictiontimesbackend.utils;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Notification;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;

import java.util.List;

public class NotificationUtils {

    private static final UserRepository userRepository = new UserRepository();

    public static void sendNotification(Notification notification) {
        try {
            userRepository.sendNotification(notification);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    public static void sendNotificationBulk(List<User> users, Notification notification) {
        Thread thread = new Thread(() -> {
            try {
                userRepository.sendNotificationBulk(users, notification);
            } catch (DatabaseOperationException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }
}
