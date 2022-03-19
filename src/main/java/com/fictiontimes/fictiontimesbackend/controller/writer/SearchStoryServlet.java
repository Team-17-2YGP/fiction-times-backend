package com.fictiontimes.fictiontimesbackend.controller.writer;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.repository.*;
import com.fictiontimes.fictiontimesbackend.service.WriterService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/writer/story/search")
public class SearchStoryServlet extends HttpServlet {
    WriterService writerService = new WriterService(new WriterRepository(), new UserRepository(),
            new StoryRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        String query = request.getParameter("q");

        if (query == null || query.equals("")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            int storyId = Integer.parseInt(query);
            List<Story> storyList = writerService.searchStoryById(storyId);
            response.getWriter().write(CommonUtils.getGson().toJson(storyList));
        } catch (NumberFormatException nfe) {
            List<Story> storyList = writerService.searchStoryByTitle(query);
            response.getWriter().write(CommonUtils.getGson().toJson(storyList));
        }
    }
}
