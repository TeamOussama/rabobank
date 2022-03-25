/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.authentication.resetpassword.exception;

import com.rabobank.socle.common.exception.HttpException;
import org.springframework.http.HttpStatus;

public class IncorrectEmailHttpException extends HttpException {
    public IncorrectEmailHttpException() {
        super("Email is invalid or doesn't registered", HttpStatus.BAD_REQUEST);
    }
}
