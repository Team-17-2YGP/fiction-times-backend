package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.DTO.StoryReviewDTO;
import com.fictiontimes.fictiontimesbackend.model.Reader;
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

@WebServlet("/reader/story/review/add")
public class AddStoryReviewServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository(),
            new WriterRepository(), new StoryRepository());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StoryReviewDTO storyReview = CommonUtils.getGson().fromJson(request.getReader(), StoryReviewDTO.class);
        int userId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
        Reader reader = new Reader();
        reader.setUserId(userId);
        storyReview.setReader(reader);
        readerService.addStoryReview(storyReview);
    }
}
