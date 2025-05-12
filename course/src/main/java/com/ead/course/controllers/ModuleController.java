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
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.dtos.ModuleRecordDto;
import com.ead.course.model.ModuleModel;
import com.ead.course.services.CourseService;
import com.ead.course.services.ModuleService;
import com.ead.course.specifications.SpecificationTemplate;

import jakarta.validation.Valid;

@RestController
public class ModuleController {

        @Autowired
        private ModuleService moduleService;

        @Autowired
        private CourseService courseService;

        @PostMapping("/courses/{courseId}/modules")
        public ResponseEntity<Object> saveModule(@PathVariable(value = "courseId") UUID courseId,
                        @RequestBody @Valid ModuleRecordDto moduleRecordDto) {
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(moduleService.save(moduleRecordDto,
                                                courseService.getCourseFindById(courseId).get()));
        }

        @GetMapping("/courses/{courseId}/modules")
        public ResponseEntity<Page<ModuleModel>> getAllModule(@PathVariable(value = "courseId") UUID courseId,
                        SpecificationTemplate.ModuleSpec spec, Pageable pageable) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(moduleService.findAllModulesIntoCourse(
                                                SpecificationTemplate.moduleCourseId(courseId).and(spec),
                                                pageable));
        }

        @GetMapping("/courses/{courseId}/modules/{moduleId}")
        public ResponseEntity<Object> getOneModule(@PathVariable(value = "courseId") UUID courseId,
                        @PathVariable(value = "moduleId") UUID moduleId) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(moduleService.findModuleIntoCourse(courseId, moduleId).get());
        }

        @DeleteMapping("/courses/{courseId}/modules/{moduleId}")
        public ResponseEntity<Object> deleteModule(@PathVariable(value = "courseId") UUID courseId,
                        @PathVariable(value = "moduleId") UUID moduleId) {
                moduleService.delete(moduleService.findModuleIntoCourse(courseId, moduleId).get());
                return ResponseEntity.status(HttpStatus.OK).body("Module deleted successfully");
        }

        @PutMapping("/courses/{courseId}/modules/{moduleId}")
        public ResponseEntity<Object> updateModule(@PathVariable(value = "courseId") UUID courseId,
                        @PathVariable(value = "moduleId") UUID moduleId,
                        @RequestBody @Valid ModuleRecordDto moduleRecordDto) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(moduleService.update(moduleRecordDto,
                                                moduleService.findModuleIntoCourse(courseId, moduleId).get()));
        }
}
