package unit;

import com.fictiontimes.fictiontimesbackend.utils.PayhereUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class PayherePropertiesTest {

    @Test
    public void testGetMerchantId() throws IOException {
        Assert.assertNotNull(PayhereUtils.getMerchantId());
    }

    @Test
    public void testMerchantSecret() throws IOException {
        Assert.assertNotNull(PayhereUtils.getMerchantSecret());
    }
}
