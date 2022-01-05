package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.BlockReasonDTO;
import com.fictiontimes.fictiontimesbackend.model.User;
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

@WebServlet("/admin/user/block/reason")
public class ReasonToBlockUserServlet extends HttpServlet {
    AdminService adminService = new AdminService(new AdminRepository(), new ReaderRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userId = Integer.parseInt(request.getParameter("id"));
        BlockReasonDTO reason = CommonUtils.getGson().fromJson(request.getReader(), BlockReasonDTO.class);
        User user = new User();
        user.setUserId(userId);
        reason.setUser(user);
        adminService.setReasonToBlockUser(reason);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        int userId = Integer.parseInt(request.getParameter("id"));
        String reason = adminService.getReasonToBlockUser(userId);
        response.getWriter().write(CommonUtils.getGson().toJson(reason));
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        int userId = Integer.parseInt(request.getParameter("id"));
        adminService.deleteReasonToBlockUser(userId);
    }
}
