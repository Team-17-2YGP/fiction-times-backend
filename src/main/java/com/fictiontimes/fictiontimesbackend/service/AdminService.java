package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.*;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayoutAdminDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.AdminPlatformStatsDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.AdminSubscriptionPaymentDTO;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayoutAdminDTO;
import com.fictiontimes.fictiontimesbackend.model.*;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;
import com.fictiontimes.fictiontimesbackend.utils.NotificationUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdminService{

    private final AdminRepository adminRepository;
    private final ReaderRepository readerRepository;

    public AdminService(AdminRepository adminRepository, ReaderRepository readerRepository) {
        this.adminRepository = adminRepository;
        this.readerRepository = readerRepository;
    }

    public List<WriterApplicant> getApplicantList() throws DatabaseOperationException {
        return adminRepository.getApplicantList();
    }

    public WriterApplicant getApplicantByUserId(int userId) throws DatabaseOperationException {
        return adminRepository.getApplicantByUserId(userId);
    }

    public void setApplicantAdminResponse(WriterApplicant applicant) throws DatabaseOperationException {
        applicant.setRespondedAt(new Date());
        applicant.setRequestedAt(new Date());
        adminRepository.setApplicantAdminResponse(applicant);
    }

    public void approveApplicant(WriterApplicant applicant) throws DatabaseOperationException {
        applicant = getApplicantByUserId(applicant.getUserId());
        adminRepository.changeApplicantUserType(applicant);
        adminRepository.createNewWriter(applicant);
        adminRepository.deleteApplicant(applicant);
    }

    public void rejectApplicant(WriterApplicant applicant) throws DatabaseOperationException {
        // TODO: Send the rejection email
        adminRepository.deleteApplicant(applicant);
        adminRepository.deleteUser(applicant);
    }

    public void blockUserByUserId(int userId) throws DatabaseOperationException {
        adminRepository.blockUserByUserId(userId);
    }

    public List<Writer> getWritersList(int limit) throws DatabaseOperationException {
        return adminRepository.getWritersList(limit);
    }

    public void unblockUserByUserId(int userId) throws DatabaseOperationException {
        adminRepository.unblockUserByUserId(userId);
    }

    public List<Writer> searchWritersByName(String userName) throws DatabaseOperationException {
        return adminRepository.searchWritersByName(userName);
    }

    public List<Writer> searchWritersById(int userId) throws DatabaseOperationException {
        return adminRepository.searchWritersById(userId);
    }

    public List<PayoutAdminDTO> getPayoutList() throws DatabaseOperationException {
        return adminRepository.getPayoutList();
    }

    public PayoutAdminDTO getPayoutById(int payoutId) throws DatabaseOperationException {
        WriterRepository writerRepository = new WriterRepository();
        Payout payout = writerRepository.getPayoutById(payoutId);
        return new PayoutAdminDTO(payout, writerRepository.findWriterById(payout.getWriterId()));
    }

    public void markPayoutCompleted(int payoutId, String paymentSlipUrl) throws DatabaseOperationException, IOException {
        adminRepository.markPayoutCompleted(payoutId, paymentSlipUrl);
        PayoutAdminDTO payout = getPayoutById(payoutId);
        EmailUtils.sendMail(payout.getWriter(), "Payout complete",
                "The payout request of LKR " + payout.getAmount() + " has been successfully " +
                        "deposited to your account: <br>" +
                        payout.getAccountNumber() + ", " + payout.getBank() + " " + payout.getBranch() + ". <br>" +
                        "View the payment slip, ", paymentSlipUrl);

        Notification notification = new Notification(
                0,
                payout.getWriterId(),
                "Payout complete",
                "LKR " + payout.getAmount() + " . " + payout.getBank(),
                CommonUtils.getFrontendAddress() + "/dashboard/writer/?page=payouts&payoutId=" + payout.getPayoutId(),
                false,
                new Timestamp(new Date().getTime())
        );
        NotificationUtils.sendNotification(notification);
    }

    public List<Reader> getReadersList(int limit) throws DatabaseOperationException {
        return adminRepository.getReadersList(limit);
    }

    public List<Reader> searchReadersByName(String userName) throws DatabaseOperationException {
        return adminRepository.searchReadersByName(userName);
    }

    public List<Reader> searchReadersById(int userId) throws DatabaseOperationException {
        return adminRepository.searchReadersById(userId);
    }
      
    public List<AdminSubscriptionPaymentDTO> getSubscriptionPaymentList(int limit, int offset) throws DatabaseOperationException {
        List<SubscriptionPayment> paymentList = adminRepository.getSubscriptionPaymentList(limit, offset);
        List<AdminSubscriptionPaymentDTO> adminSubscriptionPaymentDTO = new ArrayList<>();
        for(SubscriptionPayment subscriptionPayment : paymentList){
            adminSubscriptionPaymentDTO.add(new AdminSubscriptionPaymentDTO(subscriptionPayment,
                        readerRepository.findReaderById(subscriptionPayment.getUserId())));
        }
        return adminSubscriptionPaymentDTO;
    }

    public List<AdminSubscriptionPaymentDTO> searchSubscriptionPayments(String query) throws DatabaseOperationException {
        List<SubscriptionPayment> paymentList = adminRepository.searchSubscriptionPayments(query);
        List<AdminSubscriptionPaymentDTO> adminSubscriptionPaymentDTO = new ArrayList<>();
        for (SubscriptionPayment subscriptionPayment : paymentList) {
            adminSubscriptionPaymentDTO.add(new AdminSubscriptionPaymentDTO(subscriptionPayment,
                    readerRepository.findReaderById(subscriptionPayment.getUserId())));
        }
        return adminSubscriptionPaymentDTO;
    }

    public AdminPlatformStatsDTO getPlatformStats() throws DatabaseOperationException {
        AdminPlatformStatsDTO platformStats = adminRepository.getPlatformStats();
        platformStats.setTotalUserCount(
                platformStats.getTotalReaderCount() + platformStats.getTotalWriterCount() +
                        platformStats.getTotalAdminCount() + platformStats.getTotalApplicantCount());
        platformStats.setTotalProfitAllTime(
                platformStats.getTotalSubscriptionPaymentsAllTime() - platformStats.getTotalPayoutsAllTime());
        platformStats.setTotalProfitThisYear(
                platformStats.getTotalSubscriptionPaymentsThisYear() - platformStats.getTotalPayoutsThisYear());
        platformStats.setTotalProfit30Days(
                platformStats.getTotalSubscriptionPayments30Days() - platformStats.getTotalPayouts30Days());
        return platformStats;
    }
}
