package com.fictiontimes.fictiontimesbackend.controller.writer;

import com.fictiontimes.fictiontimesbackend.model.DTO.StoryReviewDTO;
import com.fictiontimes.fictiontimesbackend.model.Episode;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/writer/story/reviews")
public class GetReviewListServlet extends HttpServlet {

    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int storyId = Integer.parseInt(request.getParameter("id"));
        String reqLimit = request.getParameter("limit");
        int limit = (reqLimit == null) ? 20 : Integer.parseInt(reqLimit);
        String reqOffset = request.getParameter("offset");
        int offset = (reqOffset == null) ? 0 : Integer.parseInt(reqOffset);

        List<StoryReviewDTO> storyReviewList = storyService.getStoryReviewListByStoryId(storyId, userId, limit, offset);
        response.getWriter().write(CommonUtils.getGson().toJson(storyReviewList));
    }
}
