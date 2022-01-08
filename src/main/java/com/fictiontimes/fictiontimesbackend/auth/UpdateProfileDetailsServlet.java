package com.fictiontimes.fictiontimesbackend.auth;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Auth.TokenBody;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.service.WriterService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/user/update/profile")
public class UpdateProfileDetailsServlet extends HttpServlet {
    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(),
            new WriterRepository());
    WriterService writerService = new WriterService(new WriterRepository(), new UserRepository(), new StoryRepository());

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException,
            ServletException {
        TokenBody token = AuthUtils.getAuthToken(AuthUtils.extractAuthToken(request));
        try {
            switch (token.getUserType()) {
                case READER:
                    Reader readerDetails = CommonUtils.getGson().fromJson(request.getReader(), Reader.class);
                    readerDetails.setUserId(token.getUserId());
                    readerService.updateReaderProfileDetails(readerDetails);
                    break;
                case WRITER:
                    Writer writerDetails = CommonUtils.getGson().fromJson(request.getReader(), Writer.class);
                    writerDetails.setUserId(token.getUserId());
                    writerService.updateWriterProfileDetails(writerDetails);
                    break;
            }
        } catch (DatabaseOperationException e) {
            if (e.getMessage().endsWith("userName_uindex'")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("{\"error\": \"Username already exists\"}");
            } else {
                throw e;
            }
        }
    }
}
