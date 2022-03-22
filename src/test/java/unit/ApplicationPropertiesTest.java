package unit;

import com.fictiontimes.fictiontimesbackend.utils.CommonUtils;
import org.junit.Assert;
import org.junit.Test;

public class ApplicationPropertiesTest {

    @Test
    public void testGetDomain() {
        Assert.assertNotNull(CommonUtils.getDomain());
    }

    @Test
    public void testGetFrontendAddress() {
        Assert.assertNotNull(CommonUtils.getFrontendAddress());
    }
}
