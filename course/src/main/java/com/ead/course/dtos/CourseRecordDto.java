package com.ead.course.dtos;

import java.util.UUID;

import com.ead.course.enums.CourseLevel;
import com.ead.course.enums.CourseStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CourseRecordDto(
    @NotBlank(message = "Name is mandatory")
    String name,
    @NotBlank(message = "Description is mandatory")
    String description,
    @NotNull(message = "CourseStuatus is mandatory")
    CourseStatus courseStatus,
    @NotNull(message = "CourseLevel is mandatory")
    CourseLevel courseLevel,
    @NotNull(message = "UserInstructor is mandatory")
    UUID userInstructor,
    String imageUrl) {
}
