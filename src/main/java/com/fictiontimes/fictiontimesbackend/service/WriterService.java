package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.UnauthorizedActionException;
import com.fictiontimes.fictiontimesbackend.model.Payout;
import com.fictiontimes.fictiontimesbackend.model.Types.PayoutStatus;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class WriterService {
    WriterRepository writerRepository;
    UserRepository userRepository;

    public WriterService(WriterRepository writerRepository, UserRepository userRepository) {
        this.writerRepository = writerRepository;
        this.userRepository = userRepository;
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
        // Only 10,000 lkr or more withdraws are allowed, just an arbitrary value should be changed
        if (payout.getAmount() == writer.getCurrentBalance() && writer.getCurrentBalance() >= 10000) {
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
                            "<td>Amount</td>\n" +
                            "</tr>\n" +
                            "</thead>\n" +
                            "<tbody>\n" +
                            "<tr>\n" +
                            "<td>#" + savedPayout.getPayoutId() + "</td>\n" +
                            "<td>" + savedPayout.getWriterId() + "</td>\n" +
                            "<td>" + writer.getFirstName() + " " + writer.getLastName() + "</td>\n" +
                            "<td>" + savedPayout.getAmount() + "</td>\n" +
                            "</tr>\n" +
                            "</tbody>\n" +
                            "</table>",
                    CommonUtils.getFrontendAddress() + "/login"
            );
            writerRepository.resetWriterBalance(writer.getUserId());
        } else {
            throw new UnauthorizedActionException("Invalid request, the amount not accurate");
        }
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
}
