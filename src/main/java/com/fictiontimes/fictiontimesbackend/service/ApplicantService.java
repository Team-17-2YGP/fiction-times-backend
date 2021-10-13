package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.ApplicantRepository;

import java.io.IOException;
import java.util.Date;
import java.sql.SQLException;

public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public WriterApplicant getApplicantByUserId(int userId) {
        try {
            return applicantRepository.getWriterApplicantById(userId);
        } catch (SQLException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean requestReview(WriterApplicant applicant) throws SQLException, IOException, ClassNotFoundException {
        if (new Date().after(new Date(applicant.getRequestedAt().getTime() + 259200000))) {
            // TODO: Send out a notification email to the admins
            applicantRepository.updateRequestedAt(applicant);
            return true;
        }
        return false;
    }
}
