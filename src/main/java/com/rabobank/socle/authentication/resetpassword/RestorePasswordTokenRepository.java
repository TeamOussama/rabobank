/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.authentication.resetpassword;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface RestorePasswordTokenRepository extends JpaRepository<RestorePassword, Long> {

    RestorePassword findByToken(String token);

    @Modifying
    @Query("delete from RestorePassword rp where rp.expiresIn < :localDateTime")
    void deleteExpiredRestorePasswordTokens(LocalDateTime localDateTime);
}
