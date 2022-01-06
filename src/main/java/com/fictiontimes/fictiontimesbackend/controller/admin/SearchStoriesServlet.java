package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Reader;
import com.fictiontimes.fictiontimesbackend.model.Story;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/stories/search")
public class SearchStoriesServlet extends HttpServlet {
    AdminService adminService = new AdminService(new AdminRepository(), new ReaderRepository(), new StoryRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        String parameterValues = request.getParameter("search");

        if (parameterValues != null) {
            try {
                int storyId = Integer.parseInt(parameterValues);
                List<Story> storyList = adminService.searchStoryById(storyId);
                response.getWriter().write(CommonUtils.getGson().toJson(storyList));
            } catch (NumberFormatException nfe) {
                List<Story> storyList = adminService.searchStoryByTitle(parameterValues);
                response.getWriter().write(CommonUtils.getGson().toJson(storyList));
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
