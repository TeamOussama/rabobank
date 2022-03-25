/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.data.entity;

import com.rabobank.socle.data.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "role")
public class Role extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1674924613078177003L;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "is_default")
    private Boolean isDefault = false;

    @Override
    public String getAuthority() {
        return name;
    }
}
