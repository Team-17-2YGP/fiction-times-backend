package com.fictiontimes.fictiontimesbackend.Model;

import com.fictiontimes.fictiontimesbackend.Model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.Model.Types.UserType;

public class User {
    private int userId;
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String addressLane1;
    private String addressLane2;
    private String city;
    private String country;
    private String phoneNumber;
    private String profilePictureUrl;
    private UserType userType;
    private UserStatus userStatus;

    public User(int userId, String userName, String firstName,
                String lastName, String password, String email,
                String addressLane1, String addressLane2, String city,
                String country, String phoneNumber, String profilePictureUrl,
                UserType userType, UserStatus userStatus) {
        this.userId = userId;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.addressLane1 = addressLane1;
        this.addressLane2 = addressLane2;
        this.city = city;
        this.country = country;
        this.phoneNumber = phoneNumber;
        this.profilePictureUrl = profilePictureUrl;
        this.userType = userType;
        this.userStatus = userStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddressLane1() {
        return addressLane1;
    }

    public void setAddressLane1(String addressLane1) {
        this.addressLane1 = addressLane1;
    }

    public String getAddressLane2() {
        return addressLane2;
    }

    public void setAddressLane2(String addressLane2) {
        this.addressLane2 = addressLane2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
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
