package com.fictiontimes.fictiontimesbackend.model.DTO;

import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.SubscriptionPayment;

public class AdminSubscriptionPaymentDTO extends SubscriptionPayment {
    private Reader reader;

    public AdminSubscriptionPaymentDTO() {
    }

    public AdminSubscriptionPaymentDTO(SubscriptionPayment subscriptionPayment, Reader reader) {
        this.subscriptionPaymentId = subscriptionPayment.getSubscriptionPaymentId();
        this.userId = subscriptionPayment.getUserId();
        this.paymentId = subscriptionPayment.getPaymentId();
        this.subscriptionId = subscriptionPayment.getSubscriptionId();
        this.amount = subscriptionPayment.getAmount();
        this.currency = subscriptionPayment.getCurrency();
        this.paymentMethod = subscriptionPayment.getPaymentMethod();
        this.status = subscriptionPayment.getStatus();
        this.nextPaymentDate = subscriptionPayment.getNextPaymentDate();
        this.noOfPaymentsReceived = subscriptionPayment.getNoOfPaymentsReceived();
        this.timestamp = subscriptionPayment.getTimestamp();
        this.reader = reader;
    }

    public Reader getReader() {
        return reader;
    }

    public void setReader(Reader reader) {
        this.reader = reader;
    }
}
