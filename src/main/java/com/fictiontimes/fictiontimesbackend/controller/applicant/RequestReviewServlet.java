package com.fictiontimes.fictiontimesbackend.controller.applicant;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.ApplicantRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.ApplicantService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/applicant/request-review")
public class RequestReviewServlet extends HttpServlet {

    private final ApplicantService applicantService = new ApplicantService(new ApplicantRepository(new UserRepository()));

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidTokenException, TokenExpiredException, DatabaseOperationException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        WriterApplicant applicant = applicantService.getApplicantByUserId(userId);
        boolean hasRequested = applicantService.requestReview(applicant);
        if (hasRequested) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{ error: \"Last requested date is within 3 days\"}");
        }
    }
}
