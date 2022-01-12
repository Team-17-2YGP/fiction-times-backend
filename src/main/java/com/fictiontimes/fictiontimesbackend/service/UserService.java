package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.NoSuchObjectFoundException;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereFormDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.UserPasswordDTO;
import com.fictiontimes.fictiontimesbackend.model.Notification;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Types.SubscriptionStatus;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.utils.*;
import jakarta.servlet.http.Part;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createNewUser(User user) throws DatabaseOperationException {
        user.setProfilePictureUrl("https://ui-avatars.com/api/?name=" + user.getFirstName() + "+" + user.getLastName());
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        return userRepository.createNewUser(user);
    }

    public User checkCredentials(User user) throws DatabaseOperationException {
        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        User matchedUser = userRepository.findUserByUserName(user.getUserName());
        if (matchedUser != null && matchedUser.getPassword().equals(user.getPassword())) {
            return matchedUser;
        }
        return null;
    }

    public WriterApplicant registerWriterApplicant(WriterApplicant applicant) throws DatabaseOperationException, IOException {
        applicant.setRequestedAt(new Date());
        applicant.setResponse("");
        applicant.setRespondedAt(null);
        applicant = userRepository.registerWriterApplicant(applicant);
        EmailUtils.sendMail(
                applicant,
                "New Fiction Times Profile Email Verification",
                "To continue creating your new fiction times account please verify your email address.",
                CommonUtils.getDomain() + "/user/activate?token=" +
                        AuthUtils.generateVerificationToken(applicant)
        );
        return applicant;
    }

    public PayhereFormDTO registerReader(Reader reader) throws DatabaseOperationException, IOException {
        reader.setSubscriptionStatus(SubscriptionStatus.PENDING);
        reader = userRepository.registerReader(reader);
        return new PayhereFormDTO(
                reader.getFirstName(), reader.getLastName(), reader.getEmail(), reader.getPhoneNumber(),
                reader.getAddressLane1(), reader.getAddressLane2(), reader.getCity(), reader.getCountry()
        );
    }

    public User getUserByUserId(int userId) throws DatabaseOperationException {
        return userRepository.findUserByUserId(userId);
    }

    public void sendNewWriterRegistrationRequestNotification(User applicant) {
        List<User> adminUsers = userRepository.getAdminUsers();
        EmailUtils.sendEmailBulk(
                adminUsers,
                "New Writer Registration Request",
                "A new writer registration request is waiting for your review <br />"
                        + "Review Id: " +  applicant.getUserId() + "<br />",
                CommonUtils.getFrontendAddress() + "/login"
        );

        Notification notification = new Notification(
                0,
                0,
                "New writer registration request",
                applicant.getFirstName() + " " + applicant.getLastName() + " . " + applicant.getCountry(),
                CommonUtils.getFrontendAddress() + "/dashboard/admin/?page=manageApplicants",
                false,
                new Timestamp(new Date().getTime())
        );
        NotificationUtils.sendNotificationBulk(adminUsers, notification);
    }

    public void activateUserProfile(User user) {
        try {
            userRepository.activateUserProfile(user);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }

    public boolean updatePassword(int userId, UserPasswordDTO userPasswordDTO) throws DatabaseOperationException {
        User matchedUser = getUserByUserId(userId);
        String oldPasswordHash = DigestUtils.md5Hex(userPasswordDTO.getOldPassword());
        if(oldPasswordHash.equals(matchedUser.getPassword())){
            String newPasswordHash = DigestUtils.md5Hex(userPasswordDTO.getNewPassword());
            userRepository.updatePassword(userId, newPasswordHash);
            return true;
        }
        return false;
    }

    public void updateProfilePicture(int userId, Part profilePictureFile) throws DatabaseOperationException, IOException {
        User matchedUser = userRepository.findUserByUserId(userId);
        if(matchedUser.getProfilePictureUrl().startsWith("https://fiction-times-bucket.s3.ap-south-1.amazonaws.com/")){
            FileUtils.deleteFile(matchedUser.getProfilePictureUrl());
        }
        userRepository.updateProfilePicture(userId, profilePictureFile);
    }

    public List<Notification> getUnreadNotifications(int userId) throws DatabaseOperationException {
        return userRepository.getUnreadNotificationsByUserId(userId);
    }

    public void markReadNotification(int notificationId, int userId) throws DatabaseOperationException {
        userRepository.markReadNotification(notificationId, userId);
    }
    public void markReadAllNotifications(int userId) throws DatabaseOperationException {
        userRepository.markReadAllNotifications(userId);
    }

    public void requestPasswordReset(String email) throws DatabaseOperationException, NoSuchObjectFoundException, IOException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new NoSuchObjectFoundException("Couldn't find a user with the provided email");
        }
        String token = AuthUtils.generateAuthToken(user);
        String link = CommonUtils.getFrontendAddress() + "/resetPassword/?token=" + token;
        EmailUtils.sendMail(
                user,
                "Fiction Times Password Reset",
                "<p>" +
                        "Please follow the link below to reset your password" +
                        "</p>" +
                        "<p style=\"font-size: 10px\">" +
                        "Do not share this link with anybody else, specially if you didn't request a password reset" +
                        "</p>",
                link
        );
    }

    public void resetPassword(User user) throws DatabaseOperationException {
        userRepository.updatePassword(user.getUserId(), DigestUtils.md5Hex(user.getPassword()));
    }

    public String getReasonToBlock(int userId) throws DatabaseOperationException {
        return userRepository.getReasonToBlockUser(userId);
    }
}
