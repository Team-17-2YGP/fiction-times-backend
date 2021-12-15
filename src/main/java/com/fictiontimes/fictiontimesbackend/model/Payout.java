package com.fictiontimes.fictiontimesbackend.model;

import java.util.Date;

import com.fictiontimes.fictiontimesbackend.model.Types.PayoutStatus;

public class Payout {
    private int payoutId;
    private int writerId;
    private double amount;
    private String accountNumber;
    private String bank;
    private String branch;
    private Date requestedAt;
    private String paymentSlipUrl;
    private Date completedAt;
    private PayoutStatus status;

    public Payout(
            int payoutId, int writerId, double amount, String accountNumber,
            String bank, String branch, Date requestedAt,
            String paymentSlipUrl, Date completedAt, PayoutStatus status
    ) {
        this.payoutId = payoutId;
        this.writerId = writerId;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.branch = branch;
        this.requestedAt = requestedAt;
        this.paymentSlipUrl = paymentSlipUrl;
        this.completedAt = completedAt;
        this.status = status;
    }

    public int getPayoutId() {
        return payoutId;
    }

    public void setPayoutId(int payoutId) {
        this.payoutId = payoutId;
    }

    public int getWriterId() {
        return writerId;
    }

    public void setWriterId(int writerId) {
        this.writerId = writerId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public Date getRequestedAt() {
        return requestedAt;
    }

    public void setRequestedAt(Date requestedAt) {
        this.requestedAt = requestedAt;
    }

    public String getPaymentSlipUrl() {
        return paymentSlipUrl;
    }

    public void setPaymentSlipUrl(String paymentSlipUrl) {
        this.paymentSlipUrl = paymentSlipUrl;
    }

    public Date getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(Date completedAt) {
        this.completedAt = completedAt;
    }

    public PayoutStatus getStatus() {
        return status;
    }

    public void setStatus(PayoutStatus status) {
        this.status = status;
    }
}
