package com.fictiontimes.fictiontimesbackend.exception;

public class TokenNotFoundException extends Exception{
    public TokenNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
