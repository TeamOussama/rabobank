/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.authentication.resetpassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class RequestPasswordDTO {
    @Email
    @NotEmpty
    private String email;

    public RequestPasswordDTO() {
    }

    public RequestPasswordDTO(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
