package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.model.Auth.TokenBody;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.ApplicantRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ApplicantService;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.service.UserService;
import com.fictiontimes.fictiontimesbackend.service.WriterService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/me")
public class GetUserServlet extends HttpServlet {

    UserService userService = new UserService(new UserRepository());
    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(),
            new WriterRepository());
    WriterService writerService = new WriterService(new WriterRepository(), new UserRepository());
    ApplicantService applicantService = new ApplicantService(new ApplicantRepository(new UserRepository()));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, InvalidTokenException, TokenExpiredException, DatabaseOperationException {
        String tokenString = AuthUtils.extractAuthToken(request);
        TokenBody token = AuthUtils.getAuthToken(tokenString);
        int userId = token.getUserId();
        UserType userType = token.getUserType();
        switch (userType) {
            case ADMIN:
                User user = userService.getUserByUserId(userId);
                response.getWriter().write(CommonUtils.getGson().toJson(user));
                break;
            case READER:
                Reader reader = readerService.getReaderById(userId);
                response.getWriter().write(CommonUtils.getGson().toJson(reader));
                break;
            case WRITER:
                Writer writer = writerService.getWriterById(userId);
                writer.setCurrentBalance(writerService.getCurrentAmountByWriterId(writer.getUserId()));
                response.getWriter().write(CommonUtils.getGson().toJson(writer));
                break;
            case WRITER_APPLICANT:
                WriterApplicant writerApplicant = applicantService.getApplicantByUserId(userId);
                response.getWriter().write(CommonUtils.getGson().toJson(writerApplicant));
                break;
        }
    }
}
