package com.ead.course.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.course.model.CourseUserModel;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID> {

}
