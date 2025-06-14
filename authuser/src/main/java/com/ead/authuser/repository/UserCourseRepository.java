package com.ead.authuser.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.authuser.models.UserCourseModel;

public interface UserCourseRepository extends JpaRepository<UserCourseModel, UUID> {
}
