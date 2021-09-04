package com.example.fictionTimesBackend.Utils;

import com.example.fictionTimesBackend.Model.Auth.TokenBody;
import com.example.fictionTimesBackend.Model.Types.UserType;
import com.example.fictionTimesBackend.Model.User;
import jakarta.servlet.http.Cookie;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthUtils {

    private final static String secret = "motherfucking secret";

    private static String generateAuthToken(User user) {
        String token;
        TokenBody tokenBody = new TokenBody(user.getUserId(), user.getUserType(), user.getUserStatus());
        String base64Body = Base64.getEncoder().encodeToString(CommonUtils.getGson().toJson(tokenBody).getBytes());
        token = base64Body + "." + DigestUtils.sha256Hex(base64Body + secret);
        return token;
    }

    public static boolean isAuthorised(String token, UserType[] endpointRoles) {
        String[] destructuredToken = token.split("\\.");
        if (!DigestUtils.sha256Hex(destructuredToken[0] + secret).equals(destructuredToken[1])) {
            return false;
        }
        String tokenBodyString = new String(Base64.getDecoder().decode(destructuredToken[0]), StandardCharsets.UTF_8);
        TokenBody tokenBody = CommonUtils.getGson().fromJson(tokenBodyString, TokenBody.class);
        for (UserType endpointRole: endpointRoles) {
            if (endpointRole.equals(tokenBody.getUserType())) {
                return true;
            }
        }
        return false;
    }

    public static Cookie generateAuthCookie(User user) {
        Cookie cookie = new Cookie("AUTH_TOKEN", AuthUtils.generateAuthToken(user));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(18000);
        return cookie;
    }
}
