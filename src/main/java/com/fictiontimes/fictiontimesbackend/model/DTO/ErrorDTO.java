package com.fictiontimes.fictiontimesbackend.model.DTO;

public class ErrorDTO<T> {
    private String error;
    private T object;

    public ErrorDTO(String error, T object) {
        this.error = error;
        this.object = object;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
