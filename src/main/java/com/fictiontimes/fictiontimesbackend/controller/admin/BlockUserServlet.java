package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/blockUser")
public class BlockUserServlet extends HttpServlet {
    AdminService adminService = new AdminService(new AdminRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        int userId = Integer.parseInt(request.getParameter("id"));
        adminService.blockUserByUserId(userId);
    }
}
