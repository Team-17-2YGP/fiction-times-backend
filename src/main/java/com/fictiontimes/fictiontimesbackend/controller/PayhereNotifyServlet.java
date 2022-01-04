package com.fictiontimes.fictiontimesbackend.controller;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.PayhereNotifyDTO;
import com.fictiontimes.fictiontimesbackend.model.Types.PayhereMessageType;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.PayhereUtils;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@WebServlet("/payhere/notify")
@MultipartConfig
public class PayhereNotifyServlet extends HttpServlet {

    private final Logger logger = Logger.getLogger(PayhereNotifyServlet.class.getName());
    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(), new WriterRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        String merchant_id = request.getParameter("merchant_id");
        String order_id = request.getParameter("order_id");
        String payment_id = request.getParameter("payment_id");
        String subscription_id = request.getParameter("subscription_id");
        double payhere_amount = Double.parseDouble(request.getParameter("payhere_amount"));
        String payhere_currency = request.getParameter("payhere_currency");
        String status_code = request.getParameter("status_code");
        String md5sig = request.getParameter("md5sig");
        String method = request.getParameter("method");
        String status_message = request.getParameter("status_message");
        int recurring = Integer.parseInt(request.getParameter("recurring"));
        PayhereMessageType message_type = PayhereMessageType.valueOf(request.getParameter("message_type"));
        String item_recurrence = request.getParameter("item_recurrence");
        String item_duration = request.getParameter("item_duration");
        int item_rec_status = Integer.parseInt(request.getParameter("item_rec_status"));
        Date item_rec_date_next = new Date(); // fallback
        try {
            item_rec_date_next = new SimpleDateFormat("dd/MM/yyyy").parse(request.getParameter("item_rec_date_next"));
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                item_rec_date_next = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter(
                        "item_rec_date_next"));
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
        int item_rec_install_paid = Integer.parseInt(request.getParameter("item_rec_install_paid"));
        String custom_1 = request.getParameter("custom_1");
        PayhereNotifyDTO notification = new PayhereNotifyDTO(
                merchant_id, order_id, payment_id, subscription_id, payhere_amount,
                payhere_currency, status_code, md5sig, method, status_message,
                recurring, message_type, item_recurrence, item_duration,
                item_rec_status, item_rec_date_next, item_rec_install_paid, custom_1);
        if (PayhereUtils.verifyMD5Sig(notification)) {
            logger.info("Payhere notify servlet called. Timestamp [ " + new Date() + " ] Data: " + notification);
            switch (notification.getMessage_type()) {
                case AUTHORIZATION_SUCCESS:
                    readerService.verifyReaderSubscription(notification);
                    break;
                case AUTHORIZATION_FAILED:
                case RECURRING_INSTALLMENT_SUCCESS:
                case RECURRING_INSTALLMENT_FAILED:
                case RECURRING_COMPLETE:
                case RECURRING_STOPPED:
                    readerService.saveReaderPaymentDetails(notification);
                    break;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
