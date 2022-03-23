package unit;

import com.fictiontimes.fictiontimesbackend.exception.DatabaseOperationException;
import com.fictiontimes.fictiontimesbackend.model.WriterApplicant;
import com.fictiontimes.fictiontimesbackend.repository.ApplicantRepository;
import com.fictiontimes.fictiontimesbackend.service.ApplicantService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Date;

public class ApplicantServiceTest {

    private ApplicantRepository applicantRepositoryMock;
    private ApplicantService applicantService;

    @Before
    public void setup() {
        applicantRepositoryMock = Mockito.mock(ApplicantRepository.class);
        applicantService = new ApplicantService(applicantRepositoryMock);
    }

    @Test
    public void testGetApplicantByUserId() throws DatabaseOperationException {
        int userId = 1;
        WriterApplicant writerApplicantMock = new WriterApplicant();
        writerApplicantMock.setUserId(userId);
        Mockito.when(applicantRepositoryMock.getWriterApplicantById(userId))
                .thenReturn(writerApplicantMock);

        WriterApplicant writerApplicant = applicantService.getApplicantByUserId(userId);
        Assert.assertNotNull(writerApplicant);
        Assert.assertEquals(userId, writerApplicant.getUserId());
    }

    @Test
    public void testRequestReview() throws DatabaseOperationException {
        WriterApplicant writerApplicant = new WriterApplicant();
        writerApplicant.setRequestedAt(new Date());
        Assert.assertFalse(applicantService.requestReview(writerApplicant));
        writerApplicant.setRequestedAt(new Date(new Date().getTime() - 259200001));
        Assert.assertTrue(applicantService.requestReview(writerApplicant));
    }
}
