/*
 * Copyright (C) rabobank 2022. All Rights Reserved.
 * Authored by HTITI OUSSAMA
 */

package com.rabobank.socle.service;

import com.rabobank.socle.authentication.TokenService;
import com.rabobank.socle.authentication.entity.SignUpDTO;
import com.rabobank.socle.authentication.entity.Tokens;
import com.rabobank.socle.common.UserContextHolder;
import com.rabobank.socle.common.Utilities;
import com.rabobank.socle.common.exception.BadRequestHttpException;
import com.rabobank.socle.common.exception.EntityNotFoundException;
import com.rabobank.socle.common.exception.PasswordsDontMatchException;
import com.rabobank.socle.common.exception.UserAlreadyExistsHttpException;
import com.rabobank.socle.data.entity.User;
import com.rabobank.socle.data.repository.UserRepository;
import com.rabobank.socle.network.entity.ChangePasswordRequest;
import com.rabobank.socle.network.entity.UserDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
@SuppressWarnings({"checkstyle:ParameterNumber"})
public class UserService extends EntityBaseService<User> {

    public static final Integer DEFAULT_AGE = 18;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RoleService roleService;
    private final Utilities utilities;

    @Autowired
    private TokenService tokenService;

    @Value("${user.minAge}")
    private int defaultMinAge;

    @Value("${user.authorizedCountries}")
    private String authorizedCountries;

    @Autowired
    public UserService(UserRepository userRepository,
                       Utilities utilities,
                       ModelMapper modelMapper,
                       RoleService roleService) {
        super(User.class, userRepository);
        this.userRepository = userRepository;
        this.utilities = utilities;
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("User with email: " + email + " not found"));
    }

    public User register(SignUpDTO signUpDTO) {
        if (!signUpDTO.getPassword().equals(signUpDTO.getConfirmPassword())) {
            throw new PasswordsDontMatchException();
        }

        String email = signUpDTO.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsHttpException(email);
        }

        if (signUpDTO.getAge() < defaultMinAge) {
            throw new BadRequestHttpException("User should be older than " + defaultMinAge);
        }

        if (!authorizedCountries.equalsIgnoreCase(signUpDTO.getCountryCode())) {
            throw new BadRequestHttpException("Only user that live in France can create an account");
        }

        User user = signUpUser(signUpDTO);
        return userRepository.save(user);
    }

    public void changePassword(ChangePasswordRequest changePasswordRequest) {
        User user = changePasswordRequest.getUser();
        String encodedPassword = utilities.encodePassword(changePasswordRequest.getPassword());
        user.setPasswordHash(encodedPassword);
        userRepository.save(user);
    }

    public UserDTO getUserById(Long id) {
        User existingUser = getEntityByIdThrowing(id);
        return modelMapper.map(existingUser, UserDTO.class);
    }

    public UserDTO updateUserById(Long userId, UserDTO userDTO) {
        return updateUser(userId, userDTO);
    }

    public boolean deleteUser(Long id) {
        try {
            userRepository.deleteById(id);
            return true;
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(User.class, id);
        }
    }

    public UserDTO getCurrentUser() {
        User user = UserContextHolder.getUser();
        return modelMapper.map(user, UserDTO.class);
    }

    public Tokens updateCurrentUser(UserDTO userDTO) {
        User user = UserContextHolder.getUser();
        Long id = user.getId();
        UserDTO updatedUser = updateUser(id, userDTO);
        user = modelMapper.map(updatedUser, User.class);
        return tokenService.createToken(user);
    }

    public UserDTO createUser(UserDTO userDTO) {

        User user = modelMapper.map(userDTO, User.class);

        String email = user.getEmail();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsHttpException(email);
        }

        // In current version password and role are default
        user.setPasswordHash(utilities.encodePassword("testPass"));
        user.setRoles(new HashSet<>(Collections.singletonList(roleService.getDefaultRole())));

        userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    private UserDTO updateUser(Long id, UserDTO userDTO) {
        User existingUser = getEntityByIdThrowing(id);

        User updatedUser = modelMapper.map(userDTO, User.class);
        String updatedUserEmail = updatedUser.getEmail();
        if (!existingUser.getEmail().equals(updatedUserEmail)
            && userRepository.findByEmail(updatedUserEmail).isPresent()) {
            throw new UserAlreadyExistsHttpException(updatedUserEmail);
        }

        updatedUser.setId(id);
        updatedUser.setPasswordHash(existingUser.getPasswordHash());
        // Current version doesn't update roles
        updatedUser.setRoles(existingUser.getRoles());
        userRepository.save(updatedUser);

        return modelMapper.map(updatedUser, UserDTO.class);
    }

    private User signUpUser(SignUpDTO signUpDTO) {
        User user = new User();
        user.setEmail(signUpDTO.getEmail());
        user.setLogin(signUpDTO.getFullName());
        String encodedPassword = utilities.encodePassword(signUpDTO.getPassword());
        user.setPasswordHash(encodedPassword);
        user.setAge(DEFAULT_AGE);
        user.setRoles(new HashSet<>(Collections.singletonList(roleService.getDefaultRole())));

        return user;
    }
}
