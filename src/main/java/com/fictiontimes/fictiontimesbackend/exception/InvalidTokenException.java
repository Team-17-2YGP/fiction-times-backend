package com.fictiontimes.fictiontimesbackend.exception;

import jakarta.servlet.ServletException;

public class InvalidTokenException extends ServletException{
    public InvalidTokenException(String message) {
        super(message);
    }
}
