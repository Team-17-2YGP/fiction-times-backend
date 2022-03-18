package unit;

import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import org.junit.Assert;
import org.junit.Test;

public class EmailTokenTest {

    @Test
    public void testGenerateVerificationToken() {
        User user = new User();
        user.setUserId(1);
        Assert.assertNotNull(AuthUtils.generateVerificationToken(user));
    }

    @Test
    public void testVerifyVerificationToken() {
        User user = new User();
        user.setUserId(1);
        String token = AuthUtils.generateVerificationToken(user);
        Assert.assertTrue(AuthUtils.verifyVerificationToken(token));
    }

    @Test
    public void testGetUserIdByEmailToken() {
        User user = new User();
        user.setUserId(1);
        String token = AuthUtils.generateVerificationToken(user);
        Assert.assertEquals(user.getUserId(), AuthUtils.getUserIdByEmailToken(token));
    }
}
