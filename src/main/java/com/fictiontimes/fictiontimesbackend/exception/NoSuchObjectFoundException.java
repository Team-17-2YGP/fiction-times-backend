package com.fictiontimes.fictiontimesbackend.exception;

import jakarta.servlet.ServletException;

public class NoSuchObjectFoundException extends ServletException {
    public NoSuchObjectFoundException(String message){
        super(message);
    }
}
