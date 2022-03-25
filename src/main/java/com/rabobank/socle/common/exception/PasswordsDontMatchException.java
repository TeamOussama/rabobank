/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.common.exception;

import org.springframework.http.HttpStatus;

public class PasswordsDontMatchException extends HttpException {

    private static final long serialVersionUID = -7852550573176915476L;

    public PasswordsDontMatchException() {
        super("Passwords don't match", HttpStatus.BAD_REQUEST);
    }
}
