package com.fictiontimes.fictiontimesbackend.model;

import java.sql.Timestamp;

public class PlatformReport {
    private int reportId;
    private Timestamp generatedAt;
    private Timestamp reportDate;
    private int readerCount;
    private int writerCount;
    private int applicantCount;
    private int userCount;
    private int writerRegistrations;
    private int readerRegistrations;
    private double subscriptionPayments;
    private double payouts;
    private double profit;

    private double readerCountInc; // Increment percentage compared to last month
    private double writerCountInc;
    private double applicantCountInc;
    private double userCountInc;
    private double writerRegistrationsInc;
    private double readerRegistrationsInc;
    private double subscriptionPaymentsInc;
    private double payoutsInc;
    private double profitInc;

    public PlatformReport() {
    }

    public PlatformReport(int reportId, Timestamp generatedAt, Timestamp reportDate, int readerCount, int writerCount,
                          int applicantCount, int userCount, int writerRegistrations, int readerRegistrations,
                          double subscriptionPayments, double payouts, double profit,
                          double readerCountInc, double writerCountInc, double applicantCountInc, double userCountInc,
                          double writerRegistrationsInc, double readerRegistrationsInc, double subscriptionPaymentsInc,
                          double payoutsInc, double profitInc) {
        this.reportId = reportId;
        this.generatedAt = generatedAt;
        this.reportDate = reportDate;
        this.readerCount = readerCount;
        this.writerCount = writerCount;
        this.applicantCount = applicantCount;
        this.userCount = userCount;
        this.writerRegistrations = writerRegistrations;
        this.readerRegistrations = readerRegistrations;
        this.subscriptionPayments = subscriptionPayments;
        this.payouts = payouts;
        this.profit = profit;
        this.readerCountInc = readerCountInc;
        this.writerCountInc = writerCountInc;
        this.applicantCountInc = applicantCountInc;
        this.userCountInc = userCountInc;
        this.writerRegistrationsInc = writerRegistrationsInc;
        this.readerRegistrationsInc = readerRegistrationsInc;
        this.subscriptionPaymentsInc = subscriptionPaymentsInc;
        this.payoutsInc = payoutsInc;
        this.profitInc = profitInc;
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Timestamp getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(Timestamp generatedAt) {
        this.generatedAt = generatedAt;
    }

    public Timestamp getReportDate() {
        return reportDate;
    }

    public void setReportDate(Timestamp reportDate) {
        this.reportDate = reportDate;
    }

    public int getReaderCount() {
        return readerCount;
    }

    public void setReaderCount(int readerCount) {
        this.readerCount = readerCount;
    }

    public int getWriterCount() {
        return writerCount;
    }

    public void setWriterCount(int writerCount) {
        this.writerCount = writerCount;
    }

    public int getApplicantCount() {
        return applicantCount;
    }

    public void setApplicantCount(int applicantCount) {
        this.applicantCount = applicantCount;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }

    public int getWriterRegistrations() {
        return writerRegistrations;
    }

    public void setWriterRegistrations(int writerRegistrations) {
        this.writerRegistrations = writerRegistrations;
    }

    public int getReaderRegistrations() {
        return readerRegistrations;
    }

    public void setReaderRegistrations(int readerRegistrations) {
        this.readerRegistrations = readerRegistrations;
    }

    public double getSubscriptionPayments() {
        return subscriptionPayments;
    }

    public void setSubscriptionPayments(double subscriptionPayments) {
        this.subscriptionPayments = subscriptionPayments;
    }

    public double getPayouts() {
        return payouts;
    }

    public void setPayouts(double payouts) {
        this.payouts = payouts;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getReaderCountInc() {
        return readerCountInc;
    }

    public void setReaderCountInc(double readerCountInc) {
        this.readerCountInc = readerCountInc;
    }

    public double getWriterCountInc() {
        return writerCountInc;
    }

    public void setWriterCountInc(double writerCountInc) {
        this.writerCountInc = writerCountInc;
    }

    public double getApplicantCountInc() {
        return applicantCountInc;
    }

    public void setApplicantCountInc(double applicantCountInc) {
        this.applicantCountInc = applicantCountInc;
    }

    public double getUserCountInc() {
        return userCountInc;
    }

    public void setUserCountInc(double userCountInc) {
        this.userCountInc = userCountInc;
    }

    public double getWriterRegistrationsInc() {
        return writerRegistrationsInc;
    }

    public void setWriterRegistrationsInc(double writerRegistrationsInc) {
        this.writerRegistrationsInc = writerRegistrationsInc;
    }

    public double getReaderRegistrationsInc() {
        return readerRegistrationsInc;
    }

    public void setReaderRegistrationsInc(double readerRegistrationsInc) {
        this.readerRegistrationsInc = readerRegistrationsInc;
    }

    public double getSubscriptionPaymentsInc() {
        return subscriptionPaymentsInc;
    }

    public void setSubscriptionPaymentsInc(double subscriptionPaymentsInc) {
        this.subscriptionPaymentsInc = subscriptionPaymentsInc;
    }

    public double getPayoutsInc() {
        return payoutsInc;
    }

    public void setPayoutsInc(double payoutsInc) {
        this.payoutsInc = payoutsInc;
    }

    public double getProfitInc() {
        return profitInc;
    }

    public void setProfitInc(double profitInc) {
        this.profitInc = profitInc;
    }
}
