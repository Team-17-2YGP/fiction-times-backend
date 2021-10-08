package com.fictiontimes.fictiontimesbackend.exception;

public class TokenExpiredException extends Exception{
    public TokenExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
