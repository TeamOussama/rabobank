/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.authentication.resetpassword;

import com.rabobank.socle.authentication.resetpassword.exception.TokenNotFoundOrExpiredHttpException;
import com.rabobank.socle.network.entity.ChangePasswordRequest;
import com.rabobank.socle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class RestorePasswordService {

    private RestorePasswordTokenRepository restorePasswordTokenRepository;
    private UserService userService;

    @Autowired
    public RestorePasswordService(RestorePasswordTokenRepository restorePasswordTokenRepository,
                                  UserService userService) {
        this.restorePasswordTokenRepository = restorePasswordTokenRepository;
        this.userService = userService;
    }

    public void restorePassword(RestorePasswordDTO restorePasswordDTO) {
        RestorePassword restorePassword =
            restorePasswordTokenRepository.findByToken(restorePasswordDTO.getToken());

        if (Objects.isNull(restorePassword) || restorePassword.isExpired()) {
            throw new TokenNotFoundOrExpiredHttpException();
        }

        changePassword(restorePasswordDTO, restorePassword);
        restorePasswordTokenRepository.delete(restorePassword);
    }

    public void removeExpiredRestorePasswordTokens() {
        restorePasswordTokenRepository.deleteExpiredRestorePasswordTokens(LocalDateTime.now());
    }

    private void changePassword(RestorePasswordDTO restorePasswordDTO,
                                RestorePassword restorePassword) {
        ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest();
        changePasswordRequest.setUser(restorePassword.getUser());
        changePasswordRequest.setPassword(restorePasswordDTO.getNewPassword());

        userService.changePassword(changePasswordRequest);
    }

}
