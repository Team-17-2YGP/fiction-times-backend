package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereFormDTO;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Types.SubscriptionStatus;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
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
        // TODO: Add this as a notification
        List<User> adminUsers = userRepository.getAdminUsers();
        EmailUtils.sendEmailBulk(
                adminUsers,
                "New Writer Registration Request",
                "A new writer registration request is waiting for your review <br />"
                        + "Review Id: " +  applicant.getUserId() + "<br />",
                CommonUtils.getFrontendAddress() + "/login"
        );
    }

    public void activateUserProfile(User user) {
        try {
            userRepository.activateUserProfile(user);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
        }
    }
}
