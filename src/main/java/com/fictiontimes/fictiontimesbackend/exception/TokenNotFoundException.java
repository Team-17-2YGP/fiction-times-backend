package com.fictiontimes.fictiontimesbackend.exception;

import java.rmi.ServerException;

public class TokenNotFoundException extends ServerException {
    public TokenNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
