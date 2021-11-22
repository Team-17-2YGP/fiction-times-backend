package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ErrorDTO;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/reader/registration")
public class ReaderRegistrationServlet extends HttpServlet {

    private final UserService userService = new UserService(new UserRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Reader reader = CommonUtils.getGson().fromJson(request.getReader(), Reader.class);
        String payload;
        try {
            reader.setUserType(UserType.READER);
            reader.setUserStatus(UserStatus.PENDING);
            reader.setUserId(userService.createNewUser(reader).getUserId());
            payload = CommonUtils.getGson().toJson(userService.registerReader(reader));
            response.setStatus(HttpServletResponse.SC_CREATED);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
            String message = e.getMessage();
            String error;
            if (message.endsWith("userName_uindex'")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error = "User name already exists";
            } else if (message.endsWith("user_email_uindex'")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                error = "Account with the same email already exists";
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                error = message;
            }
            ErrorDTO<Reader> errorDTO = new ErrorDTO<>(error, reader);
            payload = CommonUtils.getGson().toJson(errorDTO);
        }
        response.setContentType("application/json");
        response.getWriter().write(payload);
    }
}
