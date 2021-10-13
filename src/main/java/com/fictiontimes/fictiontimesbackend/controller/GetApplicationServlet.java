package com.fictiontimes.fictiontimesbackend.controller;

import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.exception.TokenNotFoundException;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.ApplicantRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.ApplicantService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/applicant/application")
public class GetApplicationServlet extends HttpServlet {

    private final ApplicantService applicantService = new ApplicantService(new ApplicantRepository(new UserRepository()));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
            WriterApplicant applicant = applicantService.getApplicantByUserId(userId);
            response.setContentType("application/json");
            if (applicant != null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(CommonUtils.getGson().toJson(applicant));
            } else {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                response.getWriter().write("{ error: \"Couldn't find an applicant with the given user id\"}");
            }
        } catch (TokenNotFoundException | InvalidTokenException | TokenExpiredException e) {
            e.printStackTrace();
        }
    }
}
