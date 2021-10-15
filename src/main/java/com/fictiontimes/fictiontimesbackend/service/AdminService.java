package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminService{

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<WriterApplicant> getApplicantList() throws SQLException, IOException, ClassNotFoundException {
        return adminRepository.getApplicantList();
    }

    public WriterApplicant getApplicantByUserId(int userId) throws SQLException, IOException, ClassNotFoundException {
        return adminRepository.getApplicantByUserId(userId);
    }
}
