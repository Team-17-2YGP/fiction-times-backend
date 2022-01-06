package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
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

@WebServlet("/admin/story")
public class GetStoryDetailsServlet extends HttpServlet {
    AdminService adminService = new AdminService(new AdminRepository(), new ReaderRepository(), new StoryRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        int storyId = Integer.parseInt(request.getParameter("id"));
        String reqLimit = request.getParameter("limit");
        int limit = (reqLimit == null) ? 20 : Integer.parseInt(reqLimit);
        String reqOffset = request.getParameter("offset");
        int offset = (reqOffset == null) ? 0 : Integer.parseInt(reqOffset);

        adminService.getStoryDetails(storyId, limit, offset);
        response.getWriter().write(CommonUtils.getGson().toJson(adminService.getStoryDetails(storyId, limit, offset)));
    }
}
