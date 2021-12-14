package com.fictiontimes.fictiontimesbackend.controller.writer;

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

@WebServlet("/writer/payouts")
public class PayoutServlet extends HttpServlet {

    WriterService writerService;

    @Override
    public void init() throws ServletException {
        writerService = new WriterService(new WriterRepository(), new UserRepository());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String queryParam = request.getParameter("id");
        int writerId = AuthUtils.getUserId(request.getHeader("Authorization"));
        if (queryParam == null) {
            response.getWriter().write(CommonUtils.getGson().toJson(writerService.getPayoutList(writerId)));
        } else {
            int payoutId = Integer.parseInt(queryParam);
            response.getWriter().write(CommonUtils.getGson().toJson(writerService.getPayoutById(payoutId, writerId)));
        }
    }
}
