/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.common.exception;

import org.springframework.http.HttpStatus;

public class HttpException extends RuntimeException {
    private final HttpStatus httpStatus;

    public HttpException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
