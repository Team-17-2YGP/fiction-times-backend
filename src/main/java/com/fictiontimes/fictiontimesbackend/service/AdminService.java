package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.model.Writer;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.AdminRepository;

import java.util.Date;
import java.util.List;

public class AdminService{

    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<WriterApplicant> getApplicantList() throws DatabaseOperationException {
        return adminRepository.getApplicantList();
    }

    public WriterApplicant getApplicantByUserId(int userId) throws DatabaseOperationException {
        return adminRepository.getApplicantByUserId(userId);
    }

    public void setApplicantAdminResponse(WriterApplicant applicant) throws DatabaseOperationException {
        applicant.setRespondedAt(new Date());
        applicant.setRequestedAt(new Date());
        adminRepository.setApplicantAdminResponse(applicant);
    }

    public void approveApplicant(WriterApplicant applicant) throws DatabaseOperationException {
        applicant = getApplicantByUserId(applicant.getUserId());
        adminRepository.changeApplicantUserType(applicant);
        adminRepository.createNewWriter(applicant);
        adminRepository.deleteApplicant(applicant);
    }

    public void rejectApplicant(WriterApplicant applicant) throws DatabaseOperationException {
        // TODO: Send the rejection email
        adminRepository.deleteApplicant(applicant);
        adminRepository.deleteUser(applicant);
    }

    public void blockUserByUserId(int userId) throws DatabaseOperationException {
        adminRepository.blockUserByUserId(userId);
    }

    public List<Writer> getWritersList(int limit) throws DatabaseOperationException {
        return adminRepository.getWritersList(limit);
    }

    public void unblockUserByUserId(int userId) throws DatabaseOperationException {
        adminRepository.unblockUserByUserId(userId);
    }

    public List<Writer> searchWritersByName(String userName) throws DatabaseOperationException {
        return adminRepository.searchWritersByName(userName);
    }

    public List<Writer> searchWritersById(int userId) throws DatabaseOperationException {
        return adminRepository.searchWritersById(userId);
    }
}
