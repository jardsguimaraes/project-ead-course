package com.ead.course.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ead.course.model.CourseModel;

public interface CourseReporitory extends JpaRepository<CourseModel, UUID> {
}
