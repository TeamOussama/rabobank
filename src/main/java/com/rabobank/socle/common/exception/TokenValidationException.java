package com.rabobank.socle.common.exception;

public class TokenValidationException extends AuthenticationException {

    public TokenValidationException(String message) {
        super(message);
    }

    public TokenValidationException() {
    }
}
