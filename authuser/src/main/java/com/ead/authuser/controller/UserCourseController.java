package com.ead.authuser.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ead.authuser.clients.CourseClient;
import com.ead.authuser.dtos.CourseRecordDto;

@Controller
public class UserCourseController {

    @Autowired
    private CourseClient courseClient;

    @GetMapping("/users/{userId}/courses")
    public ResponseEntity<Page<CourseRecordDto>> getAllCoursesByUser(
            @PageableDefault(sort = "courseId", direction = Sort.Direction.ASC) Pageable pageable,
            @PathVariable(value = "userId") UUID userId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseClient.getAllCoursesByUser(userId, pageable));
    }
}
