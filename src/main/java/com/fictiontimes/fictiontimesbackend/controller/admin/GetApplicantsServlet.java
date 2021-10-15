package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/applicants")
public class GetApplicantsServlet extends HttpServlet {

    AdminService adminService = new AdminService(new AdminRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] parameterValues = request.getParameterValues("id");
        if (parameterValues != null) {
            try {
                WriterApplicant applicant = adminService.getApplicantByUserId(Integer.parseInt(parameterValues[0]));
                if (applicant == null) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                } else {
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.setContentType("application/json");
                    response.getWriter().write(CommonUtils.getGson().toJson(applicant));
                }
            } catch (SQLException | ClassNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        } else {
            try {
                response.setStatus(HttpServletResponse.SC_OK);
                response.setContentType("application/json");
                response.getWriter().write(CommonUtils.getGson().toJson(adminService.getApplicantList()));
            } catch (SQLException | ClassNotFoundException e) {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                e.printStackTrace();
            }
        }
    }
}
