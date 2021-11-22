package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/activate")
public class EmailVerificationServlet extends HttpServlet {

    UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        String[] parameterValues = request.getParameterValues("token");
        if (parameterValues != null) {
            String token = parameterValues[0];
            if (AuthUtils.verifyVerificationToken(token)) {
                User user = userService.getUserByUserId(AuthUtils.getUserIdByEmailToken(token));
                switch (user.getUserType()) {
                    case WRITER_APPLICANT:
                        userService.activateUserProfile(user);
                        userService.sendNewWriterRegistrationRequestNotification(user);
                        response.sendRedirect(CommonUtils.getFrontendAddress() + "/registration/activate");
                        break;
                    case READER:
                        userService.activateUserProfile(user);
                        response.sendRedirect(CommonUtils.getFrontendAddress() + "/registration/activate");
                        break;
                    default:
                        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        break;
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
