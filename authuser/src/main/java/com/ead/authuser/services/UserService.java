package com.ead.authuser.services;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.authuser.exceptions.ValidationException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> getUserFindById(UUID userId) {
        Optional<UserModel> userModelOptinal = userRepository.findById(userId);

        if (userModelOptinal.isEmpty()) {
            throw new ValidationException("Error User not found.");
        }

        return userModelOptinal;
    }
}
