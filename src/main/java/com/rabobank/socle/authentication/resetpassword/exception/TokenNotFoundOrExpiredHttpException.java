/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.authentication.resetpassword.exception;

import com.rabobank.socle.common.exception.HttpException;
import org.springframework.http.HttpStatus;

public class TokenNotFoundOrExpiredHttpException extends HttpException {
    public TokenNotFoundOrExpiredHttpException() {
        super("Reset password request wasn't performed or already expired", HttpStatus.FORBIDDEN);
    }
}
