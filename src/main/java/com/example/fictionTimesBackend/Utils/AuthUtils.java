package com.example.fictionTimesBackend.Utils;

import com.example.fictionTimesBackend.Model.Auth.TokenBody;
import com.example.fictionTimesBackend.Model.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Base64;

public class AuthUtils {

    private final static String secret = "motherfucking secret";

    public static String generateAuthToken(User user) {
        String token;
        TokenBody tokenBody = new TokenBody(user.getUserId(), user.getUserType(), user.getUserStatus());
        String base64Body = Base64.getEncoder().encodeToString(ServletUtils.getGson().toJson(tokenBody).getBytes());
        token = base64Body + "." + DigestUtils.sha256Hex(base64Body + secret);
        return token;
    }
}
