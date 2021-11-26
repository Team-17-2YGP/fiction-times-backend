package com.fictiontimes.fictiontimesbackend.model.DTO;

public class UserPasswordDTO {
    private String oldPassword;
    private String newPassword;

    public UserPasswordDTO() {}

    public UserPasswordDTO(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
