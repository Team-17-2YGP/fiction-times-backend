package com.fictiontimes.fictiontimesbackend.exception;

import jakarta.servlet.ServletException;

public class DatabaseOperationException extends ServletException {
    public DatabaseOperationException(String message) {
        super(message);
    }
}
