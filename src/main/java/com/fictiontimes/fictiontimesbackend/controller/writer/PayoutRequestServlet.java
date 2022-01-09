package com.fictiontimes.fictiontimesbackend.controller.writer;

import com.fictiontimes.fictiontimesbackend.model.Payout;
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

@WebServlet("/writer/payouts/request")
public class PayoutRequestServlet extends HttpServlet {

    WriterService writerService;

    @Override
    public void init() throws ServletException {
        writerService = new WriterService(new WriterRepository(), new UserRepository(), new StoryRepository());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Payout payout = CommonUtils.getGson().fromJson(request.getReader(), Payout.class);
        payout.setWriterId(AuthUtils.getUserId(request.getHeader("Authorization")));
        writerService.requestPayout(payout);
    }
}
