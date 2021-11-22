package com.fictiontimes.fictiontimesbackend.exception;

import jakarta.servlet.ServletException;

public class TokenExpiredException extends ServletException {
    public TokenExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
