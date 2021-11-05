package com.fictiontimes.fictiontimesbackend.controller.reader;

import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.exception.TokenNotFoundException;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.repository.UserRepository;
import com.fictiontimes.fictiontimesbackend.service.ReaderService;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/reader/notificationStatus")
public class NotificationStatusServlet extends HttpServlet {

    ReaderService readerService = new ReaderService(new UserRepository(), new ReaderRepository());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try {
            int readerId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
            String reqWriterId = request.getParameter("id");
            int writerId = Integer.parseInt(reqWriterId);

            boolean notificationStatus = readerService.getNotificationStatus(readerId, writerId);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("{\"notificationStatus\": " + notificationStatus + " }");
        } catch (TokenExpiredException | InvalidTokenException | TokenNotFoundException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\" }");
        } catch (NumberFormatException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\" }");
        }  catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\" }");
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        try {
            int readerId = AuthUtils.getUserId(AuthUtils.extractAuthToken(request));
            String reqWriterId = request.getParameter("id");
            int writerId = Integer.parseInt(reqWriterId);
            String reqSubscribe = request.getParameter("subscribe");
            boolean subscribe = Boolean.parseBoolean(reqSubscribe);
            readerService.setNotificationStatus(readerId, writerId, subscribe);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (TokenExpiredException | InvalidTokenException | TokenNotFoundException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\" }");
        } catch (NumberFormatException e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\" }");
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"error\": \"" + e.getMessage() + "\" }");
        }
    }
}