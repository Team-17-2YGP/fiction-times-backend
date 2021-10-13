package com.fictiontimes.fictiontimesbackend.model;

import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import jakarta.servlet.http.Part;

import java.util.Date;

public class WriterApplicant extends User{
    private String businessAddressLane1;
    private String businessAddressLane2;
    private String businessAddressCity;
    private String businessAddressCountry;
    private Date requestedAt;
    private String response;
    private Date respondedAt;
    private String landline;
    private String socialMediaUrls;
    private Part previousWork;
    private String previousWorkUrl;

    public WriterApplicant(
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
            String businessAddressLane1,
            String businessAddressLane2,
            String businessAddressCity,
            String businessAddressCountry,
            Date requestedAt,
            String response,
            Date respondedAt,
            String landline,
            String socialMediaUrls,
            Part previousWork) {
        super(userId, userName, firstName, lastName, password,
                email, addressLane1, addressLane2, city, country,
                phoneNumber, profilePictureUrl, userType, userStatus);
        this.businessAddressLane1 = businessAddressLane1;
        this.businessAddressLane2 = businessAddressLane2;
        this.businessAddressCity = businessAddressCity;
        this.businessAddressCountry = businessAddressCountry;
        this.requestedAt = requestedAt;
        this.response = response;
        this.respondedAt = respondedAt;
        this.landline = landline;
        this.socialMediaUrls = socialMediaUrls;
        this.previousWork = previousWork;
    }

    public WriterApplicant(
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
            String businessAddressLane1,
            String businessAddressLane2,
            String businessAddressCity,
            String businessAddressCountry,
            Date requestedAt,
            String response,
            Date respondedAt,
            String landline,
            String socialMediaUrls,
            Part previousWork) {
        super(userName, firstName, lastName, password,
                email, addressLane1, addressLane2, city, country,
                phoneNumber, profilePictureUrl, userType, userStatus);
        this.businessAddressLane1 = businessAddressLane1;
        this.businessAddressLane2 = businessAddressLane2;
        this.businessAddressCity = businessAddressCity;
        this.businessAddressCountry = businessAddressCountry;
        this.requestedAt = requestedAt;
        this.response = response;
        this.respondedAt = respondedAt;
        this.landline = landline;
        this.socialMediaUrls = socialMediaUrls;
        this.previousWork = previousWork;
    }

    public WriterApplicant() {}

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

    public String getBusinessAddressCity() {
        return businessAddressCity;
    }

    public void setBusinessAddressCity(String businessAddressCity) {
        this.businessAddressCity = businessAddressCity;
    }

    public String getBusinessAddressCountry() {
        return businessAddressCountry;
    }

    public void setBusinessAddressCountry(String businessAddressCountry) {
        this.businessAddressCountry = businessAddressCountry;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(Date respondedAt) {
        this.respondedAt = respondedAt;
    }

    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    public String getSocialMediaUrls() {
        return socialMediaUrls;
    }

    public void setSocialMediaUrls(String socialMediaUrls) {
        this.socialMediaUrls = socialMediaUrls;
    }

    public Part getPreviousWork() {
        return previousWork;
    }

    public void setPreviousWork(Part previousWork) {
        this.previousWork = previousWork;
    }

    public String getPreviousWorkUrl() {
        return previousWorkUrl;
    }

    public void setPreviousWorkUrl(String previousWorkUrl) {
        this.previousWorkUrl = previousWorkUrl;
    }
}
