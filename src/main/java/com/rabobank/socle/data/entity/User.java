/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.data.entity;

import com.rabobank.socle.data.entity.base.BaseEntity;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Builder
@Table(name = "user")
public class User extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4214325494311301431L;

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "login", nullable = false)
    @NotEmpty(message = "Please, provide a login")
    private String login;
    @Column(name = "email", nullable = false)
    @NotEmpty(message = "Please, provide an email")
    private String email;
    @Column(name = "age")
    private Integer age;
    @Column(name = "password_hash", nullable = false)
    @NotEmpty(message = "Please, provide a password")
    private String passwordHash;
    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_roles",
        joinColumns = {@JoinColumn(name = "user_id")},
        inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Role> roles;

    @CreatedDate
    private Date CreatedAt;
}
