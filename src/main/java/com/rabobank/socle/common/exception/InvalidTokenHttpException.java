/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.common.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenHttpException extends HttpException {
    private static final long serialVersionUID = 773684525186809237L;

    public InvalidTokenHttpException() {
        super(HttpStatus.FORBIDDEN);
    }
}
