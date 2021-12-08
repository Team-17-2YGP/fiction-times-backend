package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.DTO.ReaderStoryDTO;
import com.fictiontimes.fictiontimesbackend.repository.GenreRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.service.StoryService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/reader/story/new")
public class GetNewStoriesServlet extends HttpServlet {

    StoryService storyService = new StoryService(new StoryRepository(), new GenreRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int limit = Integer.parseInt(request.getParameter("limit"));
        List<ReaderStoryDTO> storyList = storyService.getRecentlyReleasedStories(limit);
        response.getWriter().write(CommonUtils.getGson().toJson(storyList));
    }
}
