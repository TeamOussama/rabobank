/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.common.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsHttpException extends HttpException {
    private static final long serialVersionUID = -5202433948475658078L;

    public UserAlreadyExistsHttpException() {
        super("Email is invalid or already taken", HttpStatus.CONFLICT);
    }

    public UserAlreadyExistsHttpException(String email) {
        super("User with email: " + email + " already registered", HttpStatus.CONFLICT);
    }
}
