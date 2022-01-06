package com.fictiontimes.fictiontimesbackend.model;

import com.fictiontimes.fictiontimesbackend.model.Types.SubscriptionStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;

public class Reader extends User{
    private SubscriptionStatus subscriptionStatus;
    private boolean isInitialized;

    public Reader(
            int userId,
            String userName,
            String firstName,
            String lastName,
            String password,
            String email,
            String addressLane1,
            String addressLane2,
            String city,
            String country,
            String phoneNumber,
            String profilePictureUrl,
            UserType userType,
            UserStatus userStatus,
            SubscriptionStatus subscriptionStatus,
            boolean isInitialized) {
        super(userId, userName, firstName, lastName, password, email,
                addressLane1, addressLane2, city, country, phoneNumber,
                profilePictureUrl, userType, userStatus);
        this.subscriptionStatus = subscriptionStatus;
        this.isInitialized = isInitialized;
    }

    public Reader() {
        super();
    }

    public SubscriptionStatus getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(SubscriptionStatus subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public void setInitialized(boolean initialized) {
        isInitialized = initialized;
    }
}
