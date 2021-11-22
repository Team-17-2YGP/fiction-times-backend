package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/applicants/assess")
public class AssessApplicantServlet extends HttpServlet {

    AdminService adminService = new AdminService(new AdminRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        WriterApplicant applicant = CommonUtils.getGson().fromJson(request.getReader(), WriterApplicant.class);
        String[] parameterValues = request.getParameterValues("action");
        if (parameterValues != null) {
            String action = parameterValues[0];
            if (action.equals("approve")) {
                adminService.approveApplicant(applicant);
            } else if (action.equals("reject")) {
                adminService.rejectApplicant(applicant);
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
