package com.fictiontimes.fictiontimesbackend.utils;

import com.fictiontimes.fictiontimesbackend.exception.InvalidTokenException;
import com.fictiontimes.fictiontimesbackend.exception.TokenExpiredException;
import com.fictiontimes.fictiontimesbackend.exception.TokenNotFoundException;
import com.fictiontimes.fictiontimesbackend.model.Auth.TokenBody;
import com.fictiontimes.fictiontimesbackend.model.Types.UserStatus;
import com.fictiontimes.fictiontimesbackend.model.Types.UserType;
import com.fictiontimes.fictiontimesbackend.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

public class AuthUtils {

    private final static String secret = "motherfucking secret";

    public static String generateAuthToken(User user) {
        String token;
        TokenBody tokenBody = new TokenBody(user.getUserId(), user.getUserType(), user.getUserStatus(),
                new Date(new Date().getTime() + 1800000));
        String base64Body = Base64.getEncoder().encodeToString(CommonUtils.getGson().toJson(tokenBody).getBytes());
        token = base64Body + "." + DigestUtils.sha256Hex(base64Body + secret);
        return token;
    }

    public static boolean isAuthorised(String token, UserType endpointRole) throws TokenExpiredException {
        String[] destructuredToken = token.split("\\.");
        if (!DigestUtils.sha256Hex(destructuredToken[0] + secret).equals(destructuredToken[1])) {
            return false;
        }
        String tokenBodyString = new String(Base64.getDecoder().decode(destructuredToken[0]), StandardCharsets.UTF_8);
        TokenBody tokenBody = CommonUtils.getGson().fromJson(tokenBodyString, TokenBody.class);
        if (tokenBody.getUserStatus() != UserStatus.ACTIVATED) return false;
        if (tokenBody.getExpDate().before(new Date())) {
            throw new TokenExpiredException("Auth token expired");
        }
        return endpointRole.equals(tokenBody.getUserType());
    }

    public static int getUserId(String token) throws TokenExpiredException, InvalidTokenException {
        String[] destructuredToken = token.split("\\.");
        if (!DigestUtils.sha256Hex(destructuredToken[0] + secret).equals(destructuredToken[1])) {
            throw new InvalidTokenException("Token not valid");
        }
        String tokenBodyString = new String(Base64.getDecoder().decode(destructuredToken[0]), StandardCharsets.UTF_8);
        TokenBody tokenBody = CommonUtils.getGson().fromJson(tokenBodyString, TokenBody.class);
        if (tokenBody.getExpDate().before(new Date())) {
            throw new TokenExpiredException("Auth token expired");
        }
        return tokenBody.getUserId();
    }

    public static String extractAuthToken(HttpServletRequest request) throws TokenNotFoundException {
        String token = request.getHeader("Authorization");
        if (token == null) throw new TokenNotFoundException("Auth token not found in the header");
        return token;
    }

    public static String generateVerificationToken(User user) {
        String base64UserId = Base64.getEncoder()
                .encodeToString(String.valueOf(user.getUserId()).getBytes(StandardCharsets.UTF_8));
        return  base64UserId + "." + DigestUtils.sha256Hex(base64UserId + secret);
    }

    public static boolean verifyVerificationToken(String token) {
        String[] destructuredToken = token.split("\\.");
        return DigestUtils.sha256Hex(destructuredToken[0] + secret).equals(destructuredToken[1]);
    }

    public static int getUserIdByEmailToken(String token) {
        String[] destructuredToken = token.split("\\.");
        return Integer.parseInt(new String(Base64.getDecoder().decode(destructuredToken[0]), StandardCharsets.UTF_8));
    }
}
