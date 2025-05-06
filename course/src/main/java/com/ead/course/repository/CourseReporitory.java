package com.ead.course.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import com.ead.course.model.CourseModel;

public interface CourseReporitory extends JpaRepository<CourseModel, UUID> {

    void delete(@NonNull CourseModel courseModel);

    boolean existsByName(String name);
}
