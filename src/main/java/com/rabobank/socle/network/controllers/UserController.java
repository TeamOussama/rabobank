/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.network.controllers;

import com.rabobank.socle.authentication.BundleUserDetailsService;
import com.rabobank.socle.authentication.entity.Tokens;
import com.rabobank.socle.common.exception.EntityNotFoundException;
import com.rabobank.socle.common.exception.HttpException;
import com.rabobank.socle.network.entity.ResponseMessage;
import com.rabobank.socle.network.entity.UserDTO;
import com.rabobank.socle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

/**
 * Controller for managing users
 */
@Controller
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get user. Allowed only for Admin
     *
     * @param id user id
     * @return user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        return ok(userService.getUserById(id));
    }

    /**
     * Update user. Allowed only for Admin
     *
     * @param id      user id
     * @param userDTO updated user data
     * @return updated user data
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserDTO userDTO)
        throws EntityNotFoundException {
        UserDTO updatedUser = userService.updateUserById(id, userDTO);
        return ok(updatedUser);
    }

    /**
     * Delete user
     *
     * @param id user id
     * @return boolean result
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseMessage> deleteUser(Authentication auth, @PathVariable Long id) {
        Long currentUserId =
            ((BundleUserDetailsService.BundleUserDetails) auth.getPrincipal()).getUser().getId();
        if (currentUserId.equals(id)) {
            throw new HttpException(
                "It is impossible to delete the current user",
                HttpStatus.BAD_REQUEST);
        }
        userService.deleteUser(id);
        return ok(new ResponseMessage("Ok"));
    }

    /**
     * Get current user
     *
     * @return current user data
     */
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        return ok(userService.getCurrentUser());
    }

    /**
     * Update current user
     *
     * @param userDTO updated user data
     * @return updated user data
     */
    @PutMapping("/current")
    @Transactional
    public ResponseEntity<Tokens> updateCurrentUser(@Valid @RequestBody UserDTO userDTO)
        throws EntityNotFoundException {
        return ok(userService.updateCurrentUser(userDTO));
    }

    /**
     * Create user. Allowed only for Admin
     *
     * @param userDTO new user data
     * @return created user
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("")
    @Transactional
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return ok(userService.createUser(userDTO));
    }
}
