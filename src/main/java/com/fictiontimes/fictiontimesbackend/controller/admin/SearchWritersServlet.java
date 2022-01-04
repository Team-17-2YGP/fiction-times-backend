package com.fictiontimes.fictiontimesbackend.controller.admin;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;
import com.fictiontimes.fictiontimesbackend.repository.ReaderRepository;
import com.fictiontimes.fictiontimesbackend.service.AdminService;
import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/writers/search")
public class SearchWritersServlet extends HttpServlet {
    AdminService adminService = new AdminService(new AdminRepository(), new ReaderRepository());
    private Object String;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, DatabaseOperationException {
        String parameterValues = request.getParameter("search");

        if (parameterValues != null) {
            try {
                int userId = Integer.parseInt(parameterValues);
                List<Writer> writerList = adminService.searchWritersById(userId);
                response.getWriter().write(CommonUtils.getGson().toJson(writerList));
            } catch (NumberFormatException nfe) {
                List<Writer> writerList = adminService.searchWritersByName(parameterValues);
                response.getWriter().write(CommonUtils.getGson().toJson(writerList));
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
