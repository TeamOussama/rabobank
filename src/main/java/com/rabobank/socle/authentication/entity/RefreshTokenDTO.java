/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.authentication.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RefreshTokenDTO {

    @NotNull
    @JsonProperty("token")
    private Tokens tokens;

    public RefreshTokenDTO() {
    }

    public RefreshTokenDTO(@NotEmpty @NotNull Tokens tokens) {
        this.tokens = tokens;
    }

    public Tokens getTokens() {
        return tokens;
    }

    public void setTokens(Tokens tokens) {
        this.tokens = tokens;
    }
}
