/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.network.entity;

import com.rabobank.socle.data.entity.User;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChangePasswordRequest {
    private User user;
    private String password;
}
