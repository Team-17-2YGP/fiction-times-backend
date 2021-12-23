package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.model.TimeData;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
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

@WebServlet("/reader/episode/time")
public class PostEpisodeTimeDataServlet extends HttpServlet {

    ReaderService readerService;

    @Override
    public void init() {
        readerService = new ReaderService(new UserRepository(), new ReaderRepository(), new WriterRepository());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TimeData timeData = CommonUtils.getGson().fromJson(request.getReader(), TimeData.class);
        int readerId = AuthUtils.getUserId(request.getHeader("Authorization"));
        timeData.setReaderId(readerId);
        readerService.saveTimeData(timeData);
    }
}
