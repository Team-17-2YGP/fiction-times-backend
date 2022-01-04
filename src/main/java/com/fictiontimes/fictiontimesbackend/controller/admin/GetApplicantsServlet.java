package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/applicants")
public class GetApplicantsServlet extends HttpServlet {

    AdminService adminService = new AdminService(new AdminRepository(), new ReaderRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        String[] parameterValues = request.getParameterValues("id");
        if (parameterValues != null) {
            WriterApplicant applicant = adminService.getApplicantByUserId(Integer.parseInt(parameterValues[0]));
            if (applicant == null) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.getWriter().write(CommonUtils.getGson().toJson(applicant));
            }
        } else {
            response.getWriter().write(CommonUtils.getGson().toJson(adminService.getApplicantList()));
        }
    }
}
