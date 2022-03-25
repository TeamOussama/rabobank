package com.rabobank.socle.common.exception;

import org.springframework.http.HttpStatus;

public class AccessTokenNotFoundHttpException extends HttpException {

    public AccessTokenNotFoundHttpException(String message, HttpStatus status) {
        super(message, status);
    }
}
