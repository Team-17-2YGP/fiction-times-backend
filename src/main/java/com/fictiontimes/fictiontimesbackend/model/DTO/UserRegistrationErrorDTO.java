package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.User;

public class UserRegistrationErrorDTO {
    private String error;
    private User user;

    public UserRegistrationErrorDTO(String error, User user) {
        this.error = error;
        this.user = user;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}