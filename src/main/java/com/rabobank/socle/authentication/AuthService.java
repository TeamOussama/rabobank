/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.authentication;

import com.rabobank.socle.authentication.entity.LoginDTO;
import com.rabobank.socle.authentication.entity.RefreshTokenDTO;
import com.rabobank.socle.authentication.entity.SignUpDTO;
import com.rabobank.socle.authentication.entity.Tokens;
import com.rabobank.socle.common.exception.EntityNotFoundException;
import com.rabobank.socle.common.exception.HttpException;
import com.rabobank.socle.common.exception.InvalidTokenHttpException;
import com.rabobank.socle.common.exception.UserAlreadyExistsHttpException;
import com.rabobank.socle.data.entity.User;
import com.rabobank.socle.service.UserService;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthService(UserService userService,
                       AuthenticationManager authenticationManager,
                       TokenService tokenService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    Tokens register(SignUpDTO signUpDTO) throws UserAlreadyExistsHttpException {
        User user = userService.register(signUpDTO);
        return createToken(user);
    }

    Tokens login(LoginDTO loginDTO) {
        try {
            Authentication authentication = createAuthentication(loginDTO);
            BundleUserDetailsService.BundleUserDetails userDetails =
                (BundleUserDetailsService.BundleUserDetails) authenticationManager
                    .authenticate(authentication).getPrincipal();
            User user = userDetails.getUser();
            return createToken(user);
        } catch (AuthenticationException exception) {
            throw new HttpException("Incorrect email or password", HttpStatus.FORBIDDEN);
        }
    }

    Tokens refreshToken(RefreshTokenDTO refreshTokenDTO) throws InvalidTokenHttpException {
        try {
            String email =
                tokenService.getEmailFromRefreshToken(refreshTokenDTO.getTokens().getRefreshToken());
            User user = userService.findByEmail(email);
            return createToken(user);
        } catch (JwtException | EntityNotFoundException e) {
            throw new InvalidTokenHttpException();
        }
    }

    private Authentication createAuthentication(LoginDTO loginDTO) {
        return new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword());
    }

    private Tokens createToken(User user) {
        return tokenService.createToken(user);
    }

}
