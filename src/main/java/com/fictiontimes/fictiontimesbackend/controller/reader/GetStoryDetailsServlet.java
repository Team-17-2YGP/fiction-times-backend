package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderStoryDTO;
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

@WebServlet("/reader/story")
public class GetStoryDetailsServlet extends HttpServlet {

    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        int storyId = Integer.parseInt(request.getParameter("id"));
        ReaderStoryDTO story = storyService.getStoryDetails(userId, storyId);
        response.getWriter().write(CommonUtils.getGson().toJson(story));
    }
}
