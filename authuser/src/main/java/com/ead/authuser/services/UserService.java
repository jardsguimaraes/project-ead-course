package com.ead.authuser.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.ead.authuser.dtos.UserRecordDto;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.especifications.EspecificationTemplate.UserSpec;
import com.ead.authuser.exceptions.NotFoundException;
import com.ead.authuser.models.UserModel;
import com.ead.authuser.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> getUserFindById(UUID userId) {
        Optional<UserModel> userModelOptinal = userRepository.findById(userId);

        if (userModelOptinal.isEmpty()) {
            throw new NotFoundException("Error User not found.");
        }

        return userModelOptinal;
    }

    public UserModel registerUser(UserRecordDto userRecordDto) {
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);

        userModel.setUserStatus(UserStatus.ACTIVE);
        userModel.setUserType(UserType.USER);
        userModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(userModel);
    }

    public UserModel updateUser(UserRecordDto userRecordDto, UserModel userModel) {
        userModel.setFullName(userRecordDto.fullName());
        userModel.setPhoneNumber(userRecordDto.phoneNumber());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return userRepository.save(userModel);
    }

    public void updatePassword(UserRecordDto userRecordDto, UserModel userModel) {
        userModel.setPassword(userRecordDto.password());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        userRepository.save(userModel);
    }

    public UserModel updateImage(UserRecordDto userRecordDto, UserModel userModel) {
        userModel.setImageUrl(userRecordDto.imageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(userModel);
    }

    public Page<UserModel> getFindAll(Specification<UserModel> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    public void delete(UUID userId) {
        userRepository.delete(getUserFindById(userId).get());
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
