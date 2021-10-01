package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.utils.PayhereUtils;

import java.io.IOException;

public class PayhereFormDTO {
    private String merchant_id;
    private String return_url;
    private String cancel_url;
    private String notify_url;
    private String first_name;
    private String last_name;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String country;
    private String order_id;
    private String items;
    private String currency;
    private String recurrence;
    private String duration;
    private double amount;
    private String custom_1;

    public PayhereFormDTO(
            String first_name, String last_name, String email, String phone,
            String addressLane1, String addressLane2, String city, String country
    ) throws IOException {
        // TODO: Replace these with proper values (proper domain, merchant id)
        this.merchant_id = PayhereUtils.getMerchantId();
        this.return_url = "<return-url>";
        this.cancel_url = "<cancel-url>";
        this.notify_url = "<base_url>/payhere/notify";
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.address = addressLane1 + addressLane2;
        this.city = city;
        this.country = country;
        this.order_id = "Order" + email;
        this.items = "Fiction Times Monthly Subscription";
        this.currency = "LKR";
        this.recurrence = "1 Month";
        this.duration = "Forever";
        this.amount = 300000.00;
        this.custom_1 = email;
    }
}
