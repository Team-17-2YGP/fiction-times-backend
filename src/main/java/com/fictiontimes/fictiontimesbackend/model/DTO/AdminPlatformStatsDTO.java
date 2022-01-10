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
    private double totalSubscriptionPaymentsLastYear;
    private double totalSubscriptionPayments30Days;
    private double totalPayoutsAllTime;
    private double totalPayoutsLastYear;
    private double totalPayouts30Days;
    private double totalProfitAllTime;
    private double totalProfitLastYear;
    private double totalProfit30Days;

    public AdminPlatformStatsDTO() {
    }

    public AdminPlatformStatsDTO(int totalReaderCount, int totalWriterCount, int totalAdminCount, int totalApplicantCount,
                                 int totalUserCount, int writerRegistrations30Days, int readerRegistrations30Days,
                                 double totalSubscriptionPaymentsAllTime, double totalSubscriptionPaymentsLastYear,
                                 double totalSubscriptionPayments30Days, double totalPayoutsAllTime, double totalPayoutsLastYear,
                                 double totalPayouts30Days, double totalProfitAllTime, double totalProfitLastYear,
                                 double totalProfit30Days) {
        this.totalReaderCount = totalReaderCount;
        this.totalWriterCount = totalWriterCount;
        this.totalAdminCount = totalAdminCount;
        this.totalApplicantCount = totalApplicantCount;
        this.totalUserCount = totalUserCount;
        this.writerRegistrations30Days = writerRegistrations30Days;
        this.readerRegistrations30Days = readerRegistrations30Days;
        this.totalSubscriptionPaymentsAllTime = totalSubscriptionPaymentsAllTime;
        this.totalSubscriptionPaymentsLastYear = totalSubscriptionPaymentsLastYear;
        this.totalSubscriptionPayments30Days = totalSubscriptionPayments30Days;
        this.totalPayoutsAllTime = totalPayoutsAllTime;
        this.totalPayoutsLastYear = totalPayoutsLastYear;
        this.totalPayouts30Days = totalPayouts30Days;
        this.totalProfitAllTime = totalProfitAllTime;
        this.totalProfitLastYear = totalProfitLastYear;
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

    public double getTotalSubscriptionPaymentsLastYear() {
        return totalSubscriptionPaymentsLastYear;
    }

    public void setTotalSubscriptionPaymentsLastYear(double totalSubscriptionPaymentsLastYear) {
        this.totalSubscriptionPaymentsLastYear = totalSubscriptionPaymentsLastYear;
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

    public double getTotalPayoutsLastYear() {
        return totalPayoutsLastYear;
    }

    public void setTotalPayoutsLastYear(double totalPayoutsLastYear) {
        this.totalPayoutsLastYear = totalPayoutsLastYear;
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

    public double getTotalProfitLastYear() {
        return totalProfitLastYear;
    }

    public void setTotalProfitLastYear(double totalProfitLastYear) {
        this.totalProfitLastYear = totalProfitLastYear;
    }

    public double getTotalProfit30Days() {
        return totalProfit30Days;
    }

    public void setTotalProfit30Days(double totalProfit30Days) {
        this.totalProfit30Days = totalProfit30Days;
    }
}
