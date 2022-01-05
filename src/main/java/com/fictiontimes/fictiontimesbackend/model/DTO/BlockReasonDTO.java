package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.User;

public class BlockReasonDTO {
    private User user;
    private String reason;

    public BlockReasonDTO() {
    }

    public BlockReasonDTO(User user, String reason) {
        this.user = user;
        this.reason = reason;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
