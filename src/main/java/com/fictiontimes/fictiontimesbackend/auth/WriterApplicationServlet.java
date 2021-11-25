package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.DTO.ErrorDTO;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

@WebServlet("/user/writer/apply")
@MultipartConfig
public class WriterApplicationServlet extends HttpServlet {

    UserService userService = new UserService(new UserRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("userName");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String addressLane1 = request.getParameter("addressLane1");
        String addressLane2 = request.getParameter("addressLane2");
        String city = request.getParameter("city");
        String country = request.getParameter("country");
        String businessAddressLane1 = request.getParameter("businessAddressLane1");
        String businessAddressLane2 = request.getParameter("businessAddressLane2");
        String businessAddressCity = request.getParameter("businessAddressCity");
        String businessAddressCountry = request.getParameter("businessAddressCountry");
        String phoneNumber = request.getParameter("phoneNumber");
        String landline = request.getParameter("landline");
        String socialMediaUrls = request.getParameter("socialMediaUrls");
        Part previousWorkPart = request.getPart("previousWork");

        WriterApplicant applicant = new WriterApplicant(
                userName, firstName, lastName, password, email, addressLane1, addressLane2, city, country,
                phoneNumber, "", UserType.WRITER_APPLICANT, UserStatus.PENDING, businessAddressLane1,
                businessAddressLane2, businessAddressCity, businessAddressCountry,
                null, null, null, landline, socialMediaUrls, previousWorkPart
        );
        String payload;
        try {
            applicant.setUserId(userService.createNewUser(applicant).getUserId());
            applicant = userService.registerWriterApplicant(applicant);
            payload = CommonUtils.getGson().toJson(applicant);
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
            ErrorDTO<WriterApplicant> errorDTO = new ErrorDTO<>(error, applicant);
            payload = CommonUtils.getGson().toJson(errorDTO);
        }
        response.getWriter().write(payload);
    }
}
