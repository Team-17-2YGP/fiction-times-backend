package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/reader/story/liked")
public class GetLikedStoriesServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(),
            new WriterRepository(), new StoryRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));

        String reqLimit = request.getParameter("limit");
        int limit = (reqLimit == null) ? 20 : Integer.parseInt(reqLimit);

        List<Story> likedStoryList = readerService.getLikedStoriesList(userId, limit);
        response.getWriter().write(CommonUtils.getGson().toJson(likedStoryList));
    }
}
