package com.fictiontimes.fictiontimesbackend.model.Auth;

import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;

import java.util.Date;

public class TokenBody {
    private int userId;
    private UserType userType;
    private UserStatus userStatus;
    private Date expDate;

    public TokenBody(int userId, UserType userType, UserStatus userStatus, Date expireDate) {
        this.userId = userId;
        this.userType = userType;
        this.userStatus = userStatus;
        this.expDate = expireDate;
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

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }
}
