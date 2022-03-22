package unit;

import com.fictiontimes.fictiontimesbackend.utils.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class AWSS3ConnectionTest {

    @Test
    public void testGetAWSClient() throws IOException {
        Assert.assertNotNull(FileUtils.getAWSClient());
    }
}
