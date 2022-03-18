package unit;

import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.model.Auth.TokenBody;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.utils.AuthUtils;
import org.junit.Assert;
import org.junit.Test;

public class AuthTest {

    @Test
    public void testGenAuthToken() {
        User user = new User();
        user.setUserId(1);
        user.setUserType(UserType.ADMIN);
        user.setUserStatus(UserStatus.ACTIVATED);
        Assert.assertNotNull(AuthUtils.generateAuthToken(user));
    }

    @Test
    public void testIsAuthorised() throws TokenExpiredException {
        User user = new User();
        user.setUserId(1);
        user.setUserType(UserType.ADMIN);
        user.setUserStatus(UserStatus.ACTIVATED);
        Assert.assertTrue(AuthUtils.isAuthorised(
                AuthUtils.generateAuthToken(user),
                UserType.ADMIN
        ));
        user.setUserStatus(UserStatus.PENDING);
        Assert.assertFalse(AuthUtils.isAuthorised(
                AuthUtils.generateAuthToken(user),
                UserType.ADMIN
        ));
        user.setUserStatus(UserStatus.ACTIVATED);
        user.setUserType(UserType.WRITER);
        Assert.assertFalse(AuthUtils.isAuthorised(
                AuthUtils.generateAuthToken(user),
                UserType.ADMIN
        ));
    }

    @Test
    public void testGetAuthToken() throws InvalidTokenException, TokenExpiredException {
        User user = new User();
        user.setUserId(1);
        user.setUserType(UserType.ADMIN);
        user.setUserStatus(UserStatus.ACTIVATED);
        TokenBody tokenBody = AuthUtils.getAuthToken(AuthUtils.generateAuthToken(user));
        Assert.assertEquals(user.getUserId(), tokenBody.getUserId());
        Assert.assertEquals(user.getUserType(), tokenBody.getUserType());
        Assert.assertEquals(user.getUserStatus(), tokenBody.getUserStatus());
    }
}
