package com.example.university.exception;

public class DuplicateEmailException extends RuntimeException {

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String resource, String email) {
        super(String.format("%s with email '%s' already exists", resource, email));
    }
}
