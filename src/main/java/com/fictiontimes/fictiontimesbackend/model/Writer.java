package com.fictiontimes.fictiontimesbackend.model;

import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;

public class Writer extends User {
    private String businessAddressLane1;
    private String businessAddressLane2;
    private String businessCity;
    private String businessCountry;
    private String landline;
    private double currentBalance;
    private String bio;

    public Writer() {
        super();
    }

    public Writer(int userId, String userName, String firstName, String lastName, String password, String email, String addressLane1, String addressLane2, String city, String country, String phoneNumber, String profilePictureUrl, UserType userType, UserStatus userStatus, String businessAddressLane1, String businessAddressLane2, String businessCity, String businessCountry, String landline, double currentBalance, String bio) {
        super(userId, userName, firstName, lastName, password, email, addressLane1, addressLane2, city, country, phoneNumber, profilePictureUrl, userType, userStatus);
        this.businessAddressLane1 = businessAddressLane1;
        this.businessAddressLane2 = businessAddressLane2;
        this.businessCity = businessCity;
        this.businessCountry = businessCountry;
        this.landline = landline;
        this.currentBalance = currentBalance;
        this.bio = bio;
    }

    public String getBusinessAddressLane1() {
        return businessAddressLane1;
    }

    public void setBusinessAddressLane1(String businessAddressLane1) {
        this.businessAddressLane1 = businessAddressLane1;
    }

    public String getBusinessAddressLane2() {
        return businessAddressLane2;
    }

    public void setBusinessAddressLane2(String businessAddressLane2) {
        this.businessAddressLane2 = businessAddressLane2;
    }

    public String getBusinessCity() {
        return businessCity;
    }

    public void setBusinessCity(String businessCity) {
        this.businessCity = businessCity;
    }

    public String getBusinessCountry() {
        return businessCountry;
    }

    public void setBusinessCountry(String businessCountry) {
        this.businessCountry = businessCountry;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(double currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
