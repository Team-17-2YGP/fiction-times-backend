package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.DTO.StoryReviewDTO;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.StoryRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.repository.WriterRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/reader/story/reviews")
public class GetStoryReviewListServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(),
            new WriterRepository(), new StoryRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int storyId = Integer.parseInt(request.getParameter("storyId"));
        String reqLimit = request.getParameter("limit");
        int limit = (reqLimit == null) ? 20 : Integer.parseInt(reqLimit);
        String reqOffset = request.getParameter("offset");
        int offset = (reqOffset == null) ? 0 : Integer.parseInt(reqOffset);

        List<StoryReviewDTO> storyReviewList = readerService.getStoryReviewList(storyId, limit, offset);
        response.getWriter().write(CommonUtils.getGson().toJson(storyReviewList));
    }
}
