package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/user/unblock")
public class UnblockUserServlet extends HttpServlet {
    AdminService adminService = new AdminService(new AdminRepository(), new ReaderRepository());

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        int userId = Integer.parseInt(request.getParameter("id"));
        adminService.unblockUserByUserId(userId);
    }
}
