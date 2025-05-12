package com.ead.course.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.model.CourseModel;
import com.ead.course.repository.CourseReporitory;
import com.ead.course.services.CourseService;
import com.ead.course.specifications.SpecificationTemplate;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseReporitory courseReporitory;

    @PostMapping
    public ResponseEntity<Object> saveCourse(@RequestBody @Valid CourseRecordDto courseRecordDto) {
        if (courseReporitory.existsByName(courseRecordDto.name())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Course name is Already Taken!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseRecordDto));
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> getAllCourses(SpecificationTemplate.CourseSpec spec, Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(courseReporitory.findAll(spec, pageable));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<Object> getOneCourse(@PathVariable(value = "courseId") UUID courseId) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.getCourseFindById(courseId).get());
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<Object> deleteCourse(@PathVariable(value = "courseId") UUID courseId) {
        courseService.delete(courseService.getCourseFindById(courseId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Course deleted successfully!");
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<Object> updateCourse(
            @PathVariable(value = "courseId") UUID courseId,
            @RequestBody @Valid CourseRecordDto courseRecordDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(courseService.update(courseRecordDto, courseService.getCourseFindById(courseId).get()));
    }

}
