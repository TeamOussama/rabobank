package com.rabobank.socle.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class Utilities {

    private PasswordEncoder passwordEncoder;

    @Autowired
    public Utilities(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
