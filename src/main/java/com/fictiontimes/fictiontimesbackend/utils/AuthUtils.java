package com.fictiontimes.fictiontimesbackend.utils;

import com.fictiontimes.fictiontimesbackend.model.Auth.TokenBody;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import com.fictiontimes.fictiontimesbackend.exception.CookieExpiredException;
import jakarta.servlet.http.Cookie;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

public class AuthUtils {

    private final static String secret = "motherfucking secret";

    private static String generateAuthToken(User user) {
        String token;
        TokenBody tokenBody = new TokenBody(user.getUserId(), user.getUserType(), user.getUserStatus(),
                new Date(new Date().getTime() + 1800000));
        String base64Body = Base64.getEncoder().encodeToString(CommonUtils.getGson().toJson(tokenBody).getBytes());
        token = base64Body + "." + DigestUtils.sha256Hex(base64Body + secret);
        return token;
    }

    public static boolean isAuthorised(String token, UserType endpointRole) throws CookieExpiredException {
        String[] destructuredToken = token.split("\\.");
        if (!DigestUtils.sha256Hex(destructuredToken[0] + secret).equals(destructuredToken[1])) {
            return false;
        }
        String tokenBodyString = new String(Base64.getDecoder().decode(destructuredToken[0]), StandardCharsets.UTF_8);
        TokenBody tokenBody = CommonUtils.getGson().fromJson(tokenBodyString, TokenBody.class);
        if (tokenBody.getUserStatus() != UserStatus.ACTIVATED) return false;
        if (tokenBody.getExpDate().before(new Date())) {
            throw new CookieExpiredException("Auth token expired");
        }
        return endpointRole.equals(tokenBody.getUserType());
    }

    public static String extractAuthToken(Cookie[] cookies) {
        Cookie authCookie = null;
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie: cookies) {
            if (cookie.getName().equals("AUTH_TOKEN"))
                authCookie = cookie;
        }
        if (authCookie == null) {
            return null;
        }
        return authCookie.getValue();
    }

    public static Cookie generateAuthCookie(User user) {
        Cookie cookie = new Cookie("AUTH_TOKEN", AuthUtils.generateAuthToken(user));
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(18000);
        return cookie;
    }

    public static Cookie removeAuthCookie() {
        Cookie cookie = new Cookie("AUTH_TOKEN", "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        return cookie;
    }
}
