package com.example.fictionTimesBackend.Model.Auth;

import com.example.fictionTimesBackend.Model.Types.UserStatus;
import com.example.fictionTimesBackend.Model.Types.UserType;

public class TokenBody {
    private int userId;
    private UserType userType;
    private UserStatus userStatus;

    public TokenBody(int userId, UserType userType, UserStatus userStatus) {
        this.userId = userId;
        this.userType = userType;
        this.userStatus = userStatus;
    }
}
