package com.taskmanager.tm.services.exceptions;

public class RequestException extends RuntimeException {

    private final String entityName;

    public RequestException(String message, String entityName) {
        super(message);
        this.entityName = entityName;
    }
}
