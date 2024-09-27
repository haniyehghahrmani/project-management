package com.example.projectManagement.exception;


public class NoUserException extends TemplateException {

    public NoUserException(String message) {
        super(message);
        setStatusCode(404);
    }
}
