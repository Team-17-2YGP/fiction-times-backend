package com.fictiontimes.fictiontimesbackend.model.DTO;

public class AdminPlatformStatsDTO {
    private int totalReaderCount;
    private int totalWriterCount;
    private int totalAdminCount;
    private int totalApplicantCount;
    private int totalUserCount;
    private int writerRegistrations30Days;
    private int readerRegistrations30Days;
    private double totalSubscriptionPaymentsAllTime;
    private double totalSubscriptionPaymentsThisYear;
    private double totalSubscriptionPayments30Days;
    private double totalPayoutsAllTime;
    private double totalPayoutsThisYear;
    private double totalPayouts30Days;
    private double totalProfitAllTime;
    private double totalProfitThisYear;
    private double totalProfit30Days;

    public AdminPlatformStatsDTO() {
    }

    public AdminPlatformStatsDTO(int totalReaderCount, int totalWriterCount, int totalAdminCount, int totalApplicantCount,
                                 int totalUserCount, int writerRegistrations30Days, int readerRegistrations30Days,
                                 double totalSubscriptionPaymentsAllTime, double totalSubscriptionPaymentsThisYear,
                                 double totalSubscriptionPayments30Days, double totalPayoutsAllTime, double totalPayoutsThisYear,
                                 double totalPayouts30Days, double totalProfitAllTime, double totalProfitThisYear,
                                 double totalProfit30Days) {
        this.totalReaderCount = totalReaderCount;
        this.totalWriterCount = totalWriterCount;
        this.totalAdminCount = totalAdminCount;
        this.totalApplicantCount = totalApplicantCount;
        this.totalUserCount = totalUserCount;
        this.writerRegistrations30Days = writerRegistrations30Days;
        this.readerRegistrations30Days = readerRegistrations30Days;
        this.totalSubscriptionPaymentsAllTime = totalSubscriptionPaymentsAllTime;
        this.totalSubscriptionPaymentsThisYear = totalSubscriptionPaymentsThisYear;
        this.totalSubscriptionPayments30Days = totalSubscriptionPayments30Days;
        this.totalPayoutsAllTime = totalPayoutsAllTime;
        this.totalPayoutsThisYear = totalPayoutsThisYear;
        this.totalPayouts30Days = totalPayouts30Days;
        this.totalProfitAllTime = totalProfitAllTime;
        this.totalProfitThisYear = totalProfitThisYear;
        this.totalProfit30Days = totalProfit30Days;
    }

    public int getTotalReaderCount() {
        return totalReaderCount;
    }

    public void setTotalReaderCount(int totalReaderCount) {
        this.totalReaderCount = totalReaderCount;
    }

    public int getTotalWriterCount() {
        return totalWriterCount;
    }

    public void setTotalWriterCount(int totalWriterCount) {
        this.totalWriterCount = totalWriterCount;
    }

    public int getTotalAdminCount() {
        return totalAdminCount;
    }

    public void setTotalAdminCount(int totalAdminCount) {
        this.totalAdminCount = totalAdminCount;
    }

    public int getTotalApplicantCount() {
        return totalApplicantCount;
    }

    public void setTotalApplicantCount(int totalApplicantCount) {
        this.totalApplicantCount = totalApplicantCount;
    }

    public int getTotalUserCount() {
        return totalUserCount;
    }

    public void setTotalUserCount(int totalUserCount) {
        this.totalUserCount = totalUserCount;
    }

    public int getWriterRegistrations30Days() {
        return writerRegistrations30Days;
    }

    public void setWriterRegistrations30Days(int writerRegistrations30Days) {
        this.writerRegistrations30Days = writerRegistrations30Days;
    }

    public int getReaderRegistrations30Days() {
        return readerRegistrations30Days;
    }

    public void setReaderRegistrations30Days(int readerRegistrations30Days) {
        this.readerRegistrations30Days = readerRegistrations30Days;
    }

    public double getTotalSubscriptionPaymentsAllTime() {
        return totalSubscriptionPaymentsAllTime;
    }

    public void setTotalSubscriptionPaymentsAllTime(double totalSubscriptionPaymentsAllTime) {
        this.totalSubscriptionPaymentsAllTime = totalSubscriptionPaymentsAllTime;
    }

    public double getTotalSubscriptionPaymentsThisYear() {
        return totalSubscriptionPaymentsThisYear;
    }

    public void setTotalSubscriptionPaymentsThisYear(double totalSubscriptionPaymentsThisYear) {
        this.totalSubscriptionPaymentsThisYear = totalSubscriptionPaymentsThisYear;
    }

    public double getTotalSubscriptionPayments30Days() {
        return totalSubscriptionPayments30Days;
    }

    public void setTotalSubscriptionPayments30Days(double totalSubscriptionPayments30Days) {
        this.totalSubscriptionPayments30Days = totalSubscriptionPayments30Days;
    }

    public double getTotalPayoutsAllTime() {
        return totalPayoutsAllTime;
    }

    public void setTotalPayoutsAllTime(double totalPayoutsAllTime) {
        this.totalPayoutsAllTime = totalPayoutsAllTime;
    }

    public double getTotalPayoutsThisYear() {
        return totalPayoutsThisYear;
    }

    public void setTotalPayoutsThisYear(double totalPayoutsThisYear) {
        this.totalPayoutsThisYear = totalPayoutsThisYear;
    }

    public double getTotalPayouts30Days() {
        return totalPayouts30Days;
    }

    public void setTotalPayouts30Days(double totalPayouts30Days) {
        this.totalPayouts30Days = totalPayouts30Days;
    }

    public double getTotalProfitAllTime() {
        return totalProfitAllTime;
    }

    public void setTotalProfitAllTime(double totalProfitAllTime) {
        this.totalProfitAllTime = totalProfitAllTime;
    }

    public double getTotalProfitThisYear() {
        return totalProfitThisYear;
    }

    public void setTotalProfitThisYear(double totalProfitThisYear) {
        this.totalProfitThisYear = totalProfitThisYear;
    }

    public double getTotalProfit30Days() {
        return totalProfit30Days;
    }

    public void setTotalProfit30Days(double totalProfit30Days) {
        this.totalProfit30Days = totalProfit30Days;
    }
}
