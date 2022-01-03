package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.model.DTO.AdminSubscriptionPaymentDTO;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/payments/search")
public class SearchSubscriptionPaymentsServlet extends HttpServlet {

    AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminService(new AdminRepository(), new ReaderRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String query = request.getParameter("query");
        if(query == null || query.equals("")){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        List<AdminSubscriptionPaymentDTO> paymentList = adminService.searchSubscriptionPayments(query);
        response.getWriter().write(CommonUtils.getGson().toJson(paymentList));
    }
}
