package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Payout;
import com.fictiontimes.fictiontimesbackend.model.Writer;

public class PayoutAdminDTO extends Payout {

    private Writer writer;

    public PayoutAdminDTO(Payout payout, Writer writer) {
        super(
                payout.getPayoutId(),
                payout.getWriterId(),
                payout.getAmount(),
                payout.getAccountNumber(),
                payout.getBank(),
                payout.getBranch(),
                payout.getRequestedAt(),
                payout.getPaymentSlipUrl(),
                payout.getCompletedAt(),
                payout.getStatus()
        );
        this.writer = writer;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }
}
