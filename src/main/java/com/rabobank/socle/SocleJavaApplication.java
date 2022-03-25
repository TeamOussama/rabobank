/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableScheduling
@SuppressWarnings({"checkstyle:FinalClass", "checkstyle:HideUtilityClassConstructor"})
public class SocleJavaApplication {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        TimeZone.setDefault(TimeZone.getTimeZone("Etc/UCT"));

        SpringApplication.run(SocleJavaApplication.class, args);
    }

}
