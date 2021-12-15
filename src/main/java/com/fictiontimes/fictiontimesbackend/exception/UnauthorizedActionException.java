package com.fictiontimes.fictiontimesbackend.exception;

import jakarta.servlet.ServletException;

public class UnauthorizedActionException extends ServletException {
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
