package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Types.PayhereMessageType;

import java.util.Date;

public class PayhereNotifyDTO {
    private String merchant_id;
    private String order_id;
    private String payment_id;
    private String subscription_id;
    private double payhere_amount;
    private String payhere_currency;
    private String status_code;
    private String md5sig;
    private String method;
    private String status_message;
    private int recurring;
    private PayhereMessageType message_type;
    private String item_recurrence;
    private String item_duration;
    private int item_rec_status;
    private Date item_rec_date_next;
    private int item_rec_install_paid;
    private String custom_1;

    public PayhereNotifyDTO(
            String merchant_id, String order_id, String payment_id, String subscription_id,
            double payhere_amount, String payhere_currency, String status_code, String md5sig,
            String method, String status_message, int recurring, PayhereMessageType message_type,
            String item_recurrence, String item_duration, int item_rec_status, Date item_rec_date_next,
            int item_rec_install_paid, String custom_1
    ) {
        this.merchant_id = merchant_id;
        this.order_id = order_id;
        this.payment_id = payment_id;
        this.subscription_id = subscription_id;
        this.payhere_amount = payhere_amount;
        this.payhere_currency = payhere_currency;
        this.status_code = status_code;
        this.md5sig = md5sig;
        this.method = method;
        this.status_message = status_message;
        this.recurring = recurring;
        this.message_type = message_type;
        this.item_recurrence = item_recurrence;
        this.item_duration = item_duration;
        this.item_rec_status = item_rec_status;
        this.item_rec_date_next = item_rec_date_next;
        this.item_rec_install_paid = item_rec_install_paid;
        this.custom_1 = custom_1;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public double getPayhere_amount() {
        return payhere_amount;
    }

    public void setPayhere_amount(double payhere_amount) {
        this.payhere_amount = payhere_amount;
    }

    public String getPayhere_currency() {
        return payhere_currency;
    }

    public void setPayhere_currency(String payhere_currency) {
        this.payhere_currency = payhere_currency;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public String getMd5sig() {
        return md5sig;
    }

    public void setMd5sig(String md5sig) {
        this.md5sig = md5sig;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

    public int getRecurring() {
        return recurring;
    }

    public void setRecurring(int recurring) {
        this.recurring = recurring;
    }

    public PayhereMessageType getMessage_type() {
        return message_type;
    }

    public void setMessage_type(PayhereMessageType message_type) {
        this.message_type = message_type;
    }

    public String getItem_recurrence() {
        return item_recurrence;
    }

    public void setItem_recurrence(String item_recurrence) {
        this.item_recurrence = item_recurrence;
    }

    public String getItem_duration() {
        return item_duration;
    }

    public void setItem_duration(String item_duration) {
        this.item_duration = item_duration;
    }

    public int getItem_rec_status() {
        return item_rec_status;
    }

    public void setItem_rec_status(int item_rec_status) {
        this.item_rec_status = item_rec_status;
    }

    public Date getItem_rec_date_next() {
        return item_rec_date_next;
    }

    public void setItem_rec_date_next(Date item_rec_date_next) {
        this.item_rec_date_next = item_rec_date_next;
    }

    public int getItem_rec_install_paid() {
        return item_rec_install_paid;
    }

    public void setItem_rec_install_paid(int item_rec_install_paid) {
        this.item_rec_install_paid = item_rec_install_paid;
    }

    public String getCustom_1() {
        return custom_1;
    }

    public void setCustom_1(String custom_1) {
        this.custom_1 = custom_1;
    }

    @Override
    public String toString() {
        return "{ " +
        " \"merchant_id\": " +  merchant_id + ", \n" +
        " \"order_id\": " +  order_id + ", \n" +
        " \"payment_id\": " +  payment_id + ", \n" +
        " \"subscription_id\": " +  subscription_id + ", \n" +
        " \"payhere_amount\": " +  payhere_amount + ", \n" +
        " \"payhere_currency\": " +  payhere_currency + ", \n" +
        " \"status_code\": " +  status_code + ", \n" +
        " \"md5sig\": " +  md5sig + ", \n" +
        " \"method\": " +  method + ", \n" +
        " \"status_message\": " +  status_message + ", \n" +
        " \"recurring\": " +  recurring + ", \n" +
        " \"message_type\": " +  message_type + ", \n" +
        " \"item_recurrence\": " +  item_recurrence + ", \n" +
        " \"item_duration\": " +  item_duration + ", \n" +
        " \"item_rec_status\": " +  item_rec_status + ", \n" +
        " \"item_rec_date_next\": " +  item_rec_date_next + ", \n" +
        " \"item_rec_install_paid\": " +  item_rec_install_paid + ", \n" +
        " \"custom_1\": " +  custom_1 +
        " }";
    }
}
