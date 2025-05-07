package com.ead.course.dtos;

import jakarta.validation.constraints.NotBlank;

public record ModuleRecordDto(
    @NotBlank(message = "Module title is required")
    String title,
    @NotBlank(message = "Module description is required")
    String description
) {
    
}
