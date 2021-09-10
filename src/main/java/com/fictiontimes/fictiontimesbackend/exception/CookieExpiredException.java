package com.fictiontimes.fictiontimesbackend.exception;

public class CookieExpiredException extends Exception{
    public CookieExpiredException(String errorMessage) {
        super(errorMessage);
    }
}
