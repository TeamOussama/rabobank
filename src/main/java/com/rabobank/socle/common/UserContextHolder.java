/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.common;

import com.rabobank.socle.authentication.BundleUserDetailsService;
import com.rabobank.socle.data.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserContextHolder {

    private UserContextHolder() {
    }

    public static User getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BundleUserDetailsService.BundleUserDetails userDetails =
            (BundleUserDetailsService.BundleUserDetails) principal;
        return userDetails.getUser();
    }
}
