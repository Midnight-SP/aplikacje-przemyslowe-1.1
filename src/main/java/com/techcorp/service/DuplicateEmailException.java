package com.techcorp.service;

// Wyjątek rzucany przy próbie dodania pracownika z emailem, który już istnieje w systemie.
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Pracownik z emailem '" + email + "' już istnieje");
    }
}
