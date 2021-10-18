package com.fictiontimes.fictiontimesbackend.auth;

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
import java.sql.SQLException;

@WebServlet("/user/activate")
public class EmailVerificationServlet extends HttpServlet {

    UserService userService = new UserService(new UserRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] parameterValues = request.getParameterValues("token");
        if (parameterValues != null) {
            String token = parameterValues[0];
            if (AuthUtils.verifyVerificationToken(token)) {
                try {
                    User user = userService.getUserByUserId(AuthUtils.getUserIdByEmailToken(token));
                    switch (user.getUserType()) {
                        case WRITER_APPLICANT:
                            userService.activateUserProfile(user);
                            userService.sendNewWriterRegistrationRequestNotification(user);
                            response.sendRedirect(CommonUtils.getFrontendAddress() + "/registration/activated");
                            break;
                        case READER:
                            userService.activateUserProfile(user);
                            response.sendRedirect(CommonUtils.getFrontendAddress() + "/registration/activated");
                            break;
                        default:
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            break;
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
                    response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
