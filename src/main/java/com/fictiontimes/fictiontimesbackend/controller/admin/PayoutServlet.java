package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import com.fictiontimes.fictiontimesbackend.utils.FileUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet("/admin/payouts")
@MultipartConfig
public class PayoutServlet extends HttpServlet {

    AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminService(new AdminRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryParam = request.getParameter("id");
        if (queryParam == null) {
            // TODO: Add a limit and a offset to manage pagination
            response.getWriter().write(CommonUtils.getGson().toJson(adminService.getPayoutList()));
        } else {
            int payoutId = Integer.parseInt(queryParam);
            response.getWriter().write(CommonUtils.getGson().toJson(adminService.getPayoutById(payoutId)));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int payoutId = Integer.parseInt(request.getParameter("id"));
        Part paymentSlip = request.getPart("paymentSlip");
        String paymentSlipUrl = FileUtils.saveFile(
                paymentSlip, "paymentSlips/" + payoutId
        );
        adminService.markPayoutCompleted(payoutId, paymentSlipUrl);
    }
}
