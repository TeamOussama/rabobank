/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.network.entity;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    private Long id;

    @NotEmpty
    @NotNull
    private String login;

    @NotEmpty
    @NotNull
    private String email;

    private String firstName;
    private String lastName;
    private Integer age;
    private Set<String> roles;

    public UserDTO(String login, String email) {
        this.email = email;
        this.login = login;
    }
}
