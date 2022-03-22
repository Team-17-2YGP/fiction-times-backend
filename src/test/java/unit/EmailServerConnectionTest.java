package unit;

import com.fictiontimes.fictiontimesbackend.utils.EmailUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class EmailServerConnectionTest {

    @Test
    public void testGetEmailSession() throws IOException {
        Assert.assertNotNull(EmailUtils.getSession());
    }
}
