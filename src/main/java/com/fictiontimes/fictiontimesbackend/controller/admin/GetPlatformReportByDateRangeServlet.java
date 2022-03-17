package com.fictiontimes.fictiontimesbackend.controller.admin;

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
import java.sql.Timestamp;

@WebServlet("/admin/report/range")
public class GetPlatformReportByDateRangeServlet extends HttpServlet {

    AdminService adminService;

    @Override
    public void init() throws ServletException {
        adminService = new AdminService(new AdminRepository(), new ReaderRepository());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String strStartDate = request.getParameter("startDate");
        String strEndDate = request.getParameter("endDate");
        Timestamp startDate = Timestamp.valueOf(strStartDate + " 00:00:00");
        Timestamp endDate = Timestamp.valueOf(strEndDate + " 00:00:00");
        response.getWriter().write(CommonUtils.getGson().toJson(adminService.getPlatformReportByDateRange(startDate, endDate)));
    }
}
