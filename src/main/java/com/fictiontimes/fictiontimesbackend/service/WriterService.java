package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.UnauthorizedActionException;
import com.fictiontimes.fictiontimesbackend.model.*;
import com.fictiontimes.fictiontimesbackend.model.DTO.WriterStatsDTO;
import com.fictiontimes.fictiontimesbackend.model.Types.PayoutStatus;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;
import com.fictiontimes.fictiontimesbackend.utils.NotificationUtils;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class WriterService {
    WriterRepository writerRepository;
    UserRepository userRepository;
    StoryRepository storyRepository;

    public WriterService(WriterRepository writerRepository, UserRepository userRepository, StoryRepository storyRepository) {
        this.writerRepository = writerRepository;
        this.userRepository = userRepository;
        this.storyRepository = storyRepository;
    }

    public Writer getWriterById(int writerId) throws DatabaseOperationException {
        return writerRepository.findWriterById(writerId);
    }

    public void updateWriterProfileDetails(Writer writerDetails) throws DatabaseOperationException {
        userRepository.updateUserDetails(writerDetails);
        writerRepository.updateWriterDetails(writerDetails);
    }

    public void requestPayout(Payout payout) throws DatabaseOperationException, IOException, UnauthorizedActionException {
        Writer writer = getWriterById(payout.getWriterId());
        double currentAmount = getCurrentAmountByWriterId(writer.getUserId());
        // Only 10,000 lkr or more withdraws are allowed, just an arbitrary value should be changed
        if (currentAmount >= 10000) {
            payout.setAmount(currentAmount);
            payout.setStatus(PayoutStatus.PENDING);
            payout.setRequestedAt(new Date());
            Payout savedPayout = writerRepository.requestPayout(payout);
            List<User> adminUsers = userRepository.getAdminUsers();
            EmailUtils.sendEmailBulk(
                    adminUsers,
                    "New writer payout request",
                    "A new writer payout request is waiting for your review </br> </br>" +
                            "<table style=\"border-collapse: collapse; width: 100%;\" border=\"2\">\n" +
                            "<thead>\n" +
                            "<tr>\n" +
                            "<td>Payout Id</td>\n" +
                            "<td>Writer Id</td>\n" +
                            "<td>Name</td>\n" +
                            "<td>Email</td>\n" +
                            "<td>Phone</td>\n" +
                            "<td>Amount</td>\n" +
                            "<td>Bank Details</td>\n" +
                            "</tr>\n" +
                            "</thead>\n" +
                            "<tbody>\n" +
                            "<tr>\n" +
                            "<td>#" + savedPayout.getPayoutId() + "</td>\n" +
                            "<td>" + savedPayout.getWriterId() + "</td>\n" +
                            "<td>" + writer.getFirstName() + " " + writer.getLastName() + "</td>\n" +
                            "<td>" + writer.getEmail() + "</td>\n" +
                            "<td>" + writer.getPhoneNumber() + ", " + writer.getLandline() + "</td>\n" +
                            "<td>" + savedPayout.getAmount() + "</td>\n" +
                            "<td> Account No: " + savedPayout.getAccountNumber() +
                            "</br> Bank: " + savedPayout.getBank() +
                            "</br> Branch: " + savedPayout.getBranch() +
                            "</td>\n" +
                            "</tr>\n" +
                            "</tbody>\n" +
                            "</table>",
                    CommonUtils.getFrontendAddress() + "/login"
            );

            Notification notification = new Notification(
                    0,
                    0,
                    "New writer payout request",
                    writer.getFirstName() + " " + writer.getLastName() + " . LKR " + Math.round(payout.getAmount()*100)/100,
                    CommonUtils.getFrontendAddress() + "/dashboard/admin/?page=transactions",
                    false,
                    new Timestamp(new Date().getTime())
            );
            NotificationUtils.sendNotificationBulk(adminUsers, notification);

            writerRepository.resetWriterBalance(writer.getUserId());
        } else {
            throw new UnauthorizedActionException("Invalid request, not enough balance to request a payoust");
        }
    }

    public double getCurrentAmountByWriterId(int writerId) throws DatabaseOperationException {
        long milliSeconds = writerRepository.getMilliSecondsSinceTheLastPayout(writerId);
        // 1 view -> 5 minutes of read time
        // 3.6 rupees -> 5 min
        // 1 ms -> (3*10^-4)/25 rupees
        return milliSeconds*Math.pow(10, -4)*(3.0/25);
    }

    public List<Payout> getPayoutList(int writerId) throws DatabaseOperationException {
        return writerRepository.getPayoutList(writerId);
    }

    public Payout getPayoutById(int payoutId, int writerId) throws UnauthorizedActionException, DatabaseOperationException {
        Payout payout = writerRepository.getPayoutById(payoutId);
        if (payout.getWriterId() != writerId) {
            throw new UnauthorizedActionException("Can't access the requested payout");
        }
        return payout;
    }

    public WriterStatsDTO getStats(int writerId) throws DatabaseOperationException {
        WriterStatsDTO stats =  writerRepository.getStats(writerId);
        List<Story> stories = storyRepository.getStoryListByUserId(writerId);
        stats.setStories(stories);
        return stats;
    }

    public List<Story> searchStoryByTitle(String storyTitle) throws DatabaseOperationException {
        return storyRepository.searchStoryByTitle(storyTitle);
    }

    public List<Story> searchStoryById(int storyId) throws DatabaseOperationException {
        return storyRepository.searchStoryById(storyId);
    }
}
