package com.fictiontimes.fictiontimesbackend.controller.writer;

import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.WriterService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/writer/stats")
public class getStatisticsServlet extends HttpServlet {

    WriterService writerService = new WriterService(new WriterRepository(), new UserRepository(), new StoryRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        response.getWriter().write(CommonUtils.getGson().toJson(writerService.getStats(userId)));
    }
}
