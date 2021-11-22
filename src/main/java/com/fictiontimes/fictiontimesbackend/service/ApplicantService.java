package com.fictiontimes.fictiontimesbackend.service;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.ApplicantRepository;

import java.util.Date;

public class ApplicantService {

    private final ApplicantRepository applicantRepository;

    public ApplicantService(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    public WriterApplicant getApplicantByUserId(int userId) {
        try {
            return applicantRepository.getWriterApplicantById(userId);
        } catch (DatabaseOperationException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean requestReview(WriterApplicant applicant) throws DatabaseOperationException {
        if (new Date().after(new Date(applicant.getRequestedAt().getTime() + 259200000))) {
            // TODO: Send out a notification email to the admins
            applicantRepository.updateRequestedAt(applicant);
            return true;
        }
        return false;
    }
}
