package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
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

    public void setApplicantAdminResponse(WriterApplicant applicant) throws SQLException, IOException, ClassNotFoundException {
        applicant.setRespondedAt(new Date());
        applicant.setRequestedAt(new Date());
        adminRepository.setApplicantAdminResponse(applicant);
    }

    public void approveApplicant(WriterApplicant applicant) throws SQLException, IOException, ClassNotFoundException {
        applicant = getApplicantByUserId(applicant.getUserId());
        adminRepository.changeApplicantUserType(applicant);
        adminRepository.createNewWriter(applicant);
        adminRepository.deleteApplicant(applicant);
    }

    public void rejectApplicant(WriterApplicant applicant) throws SQLException, IOException, ClassNotFoundException {
        // TODO: Send the rejection email
        adminRepository.deleteApplicant(applicant);
        adminRepository.deleteUser(applicant);
    }
}
