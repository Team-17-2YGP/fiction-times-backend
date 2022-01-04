package com.fictiontimes.fictiontimesbackend.model;

import com.fictiontimes.fictiontimesbackend.model.Types.PayhereMessageType;

import java.sql.Timestamp;

public class SubscriptionPayment {
    protected int subscriptionPaymentId;
    protected int userId;
    protected String paymentId;
    protected String subscriptionId;
    protected float amount;
    protected String currency;
    protected String paymentMethod;
    protected PayhereMessageType status;
    protected Timestamp nextPaymentDate;
    protected int noOfPaymentsReceived;
    protected Timestamp timestamp;

    public SubscriptionPayment() {
    }

    public SubscriptionPayment(int subscriptionPaymentId, int userId, String paymentId, String subscriptionId, float amount, String currency, String paymentMethod, PayhereMessageType status, Timestamp nextPaymentDate, int noOfPaymentsReceived, Timestamp timestamp) {
        this.subscriptionPaymentId = subscriptionPaymentId;
        this.userId = userId;
        this.paymentId = paymentId;
        this.subscriptionId = subscriptionId;
        this.amount = amount;
        this.currency = currency;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.nextPaymentDate = nextPaymentDate;
        this.noOfPaymentsReceived = noOfPaymentsReceived;
        this.timestamp = timestamp;
    }

    public int getSubscriptionPaymentId() {
        return subscriptionPaymentId;
    }

    public void setSubscriptionPaymentId(int subscriptionPaymentId) {
        this.subscriptionPaymentId = subscriptionPaymentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public PayhereMessageType getStatus() {
        return status;
    }

    public void setStatus(PayhereMessageType status) {
        this.status = status;
    }

    public Timestamp getNextPaymentDate() {
        return nextPaymentDate;
    }

    public void setNextPaymentDate(Timestamp nextPaymentDate) {
        this.nextPaymentDate = nextPaymentDate;
    }

    public int getNoOfPaymentsReceived() {
        return noOfPaymentsReceived;
    }

    public void setNoOfPaymentsReceived(int noOfPaymentsReceived) {
        this.noOfPaymentsReceived = noOfPaymentsReceived;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
