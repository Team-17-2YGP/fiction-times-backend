package com.fictiontimes.fictiontimesbackend.Model.Auth;

import com.fictiontimes.fictiontimesbackend.Model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.Model.Types.UserType;

public class TokenBody {
    private int userId;
    private UserType userType;
    private UserStatus userStatus;

    public TokenBody(int userId, UserType userType, UserStatus userStatus) {
        this.userId = userId;
        this.userType = userType;
        this.userStatus = userStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
}
