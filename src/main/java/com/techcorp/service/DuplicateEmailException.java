package com.techcorp.service;

/**
 * Wyjątek zgłaszany przy próbie dodania pracownika z istniejącym adresem email.
 */
public class DuplicateEmailException extends RuntimeException {
    public DuplicateEmailException(String email) {
        super("Pracownik z emailem '" + email + "' już istnieje");
    }
}
