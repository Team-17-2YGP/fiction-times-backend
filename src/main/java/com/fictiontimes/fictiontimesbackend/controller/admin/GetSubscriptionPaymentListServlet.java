package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.AdminSubscriptionPaymentDTO;
import com.fictiontimes.fictiontimesbackend.model.SubscriptionPayment;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/payments")
public class GetSubscriptionPaymentListServlet extends HttpServlet {

    AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminService(new AdminRepository(), new ReaderRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String reqLimit = request.getParameter("limit");
        int limit = (reqLimit == null) ? 20 : Integer.parseInt(reqLimit);
        String reqOffset = request.getParameter("offset");
        int offset = (reqOffset == null) ? 0 : Integer.parseInt(reqOffset);

        List<AdminSubscriptionPaymentDTO> paymentList = adminService.getSubscriptionPaymentList(limit, offset);
        response.getWriter().write(CommonUtils.getGson().toJson(paymentList));
    }
}
