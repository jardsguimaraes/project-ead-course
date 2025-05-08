package com.ead.course.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.model.LessonModel;
import com.ead.course.services.LessonService;
import com.ead.course.services.ModuleService;

import jakarta.validation.Valid;

@RestController
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @Autowired
    private ModuleService moduleService;

    @PostMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<Object> saveLesson(@PathVariable(value = "moduleId") UUID moduleId,
            @RequestBody @Valid LessonRecordDto lessonRecordDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(lessonService.save(moduleService.findByID(moduleId).get(), lessonRecordDto));
    }

    @GetMapping("/modules/{moduleId}/lessons")
    public ResponseEntity<List<LessonModel>> getAllLessons(@PathVariable(value = "moduleId") UUID moduleId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonService.findAllLessonsIntoModule(moduleId));
    }

    @GetMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> getOneLesson(@PathVariable(value = "moduleId") UUID moduleId,
            @PathVariable(value = "lessonId") UUID lessonId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonService.findLessonsIntoModule(moduleId, lessonId).get());
    }

    @DeleteMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> deleteLesson(@PathVariable(value = "moduleId") UUID moduleId,
            @PathVariable(value = "lessonId") UUID lessonId) {
        lessonService.delete(lessonService.findLessonsIntoModule(moduleId, lessonId).get());
        return ResponseEntity.status(HttpStatus.OK).body("Lesson deleted successfully");
    }

    @PutMapping("/modules/{moduleId}/lessons/{lessonId}")
    public ResponseEntity<Object> updateLesson(@PathVariable(value = "moduleId") UUID moduleId,
            @PathVariable(value = "lessonId") UUID lessonId,
            @RequestBody @Valid LessonRecordDto lessonRecordDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(lessonService.update(lessonRecordDto,
                        lessonService.findLessonsIntoModule(moduleId, lessonId).get()));
    }
}
