package com.ead.course.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.course.dtos.LessonRecordDto;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.LessonRepository;

@Service
public class LessonService {

    @Autowired
    private LessonRepository lessonRepository;

    public LessonModel save(ModuleModel moduleModel, LessonRecordDto lessonRecordDto) {
        LessonModel lessonModel = new LessonModel();
        BeanUtils.copyProperties(lessonRecordDto, lessonModel);
        lessonModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lessonModel.setModule(moduleModel);

        return lessonRepository.save(lessonModel);
    }

    public List<LessonModel> findAllLessonsIntoModule(UUID moduleId) {
        return lessonRepository.findAllLessonsIntoModule(moduleId);
    }

    public Optional<LessonModel> findLessonsIntoModule(UUID moduleId, UUID lessonId) {
        Optional<LessonModel> lessonRepositoryOptional = lessonRepository.findLessonsIntoModule(moduleId, lessonId);

        if (lessonRepositoryOptional.isEmpty()) {
            throw new NotFoundException("Error: Lesson not found for this module");
        }
        return lessonRepositoryOptional;
    }

    public void delete(LessonModel lessonModel) {
        lessonRepository.delete(lessonModel);
    }

    public LessonModel update(LessonRecordDto lessonRecordDto, LessonModel lessonModel) {
        BeanUtils.copyProperties(lessonRecordDto, lessonModel);
        return lessonRepository.save(lessonModel);
    }
}
