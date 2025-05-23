package com.ead.course.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ead.course.dtos.ModuleRecordDto;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.model.CourseModel;
import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;

@Service
public class ModuleService {

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public void delete(ModuleModel moduleModel) {
        List<LessonModel> lessonModuleList = lessonRepository.findAllLessonsIntoModule(moduleModel.getModuleId());

        if (!lessonModuleList.isEmpty()) {
            lessonRepository.deleteAll(lessonModuleList);
        }

        moduleRepository.delete(moduleModel);
    }

    public ModuleModel save(ModuleRecordDto moduleRecordDto, CourseModel courseModel) {
        var moduleModel = new ModuleModel();
        BeanUtils.copyProperties(moduleRecordDto, moduleModel);
        moduleModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        moduleModel.setCourse(courseModel);

        return moduleRepository.save(moduleModel);
    }

    public List<ModuleModel> findAllModulesIntoCourse(UUID courseId) {
        return moduleRepository.findAllModulesIntoCourse(courseId);
    }

    public Optional<ModuleModel> findModuleIntoCourse(UUID courseId, UUID moduleId) {
        Optional<ModuleModel> moduleRepositoryOptional = moduleRepository.findModuleIntoCourse(courseId, moduleId);

        if (moduleRepositoryOptional.isEmpty()) {
            throw new NotFoundException("Error: Module not found for this course");
        }
        return moduleRepositoryOptional;
    }

    public ModuleModel update(ModuleRecordDto moduleRecordDto, ModuleModel moduleModel) {
        BeanUtils.copyProperties(moduleRecordDto, moduleModel);
        return moduleRepository.save(moduleModel);
    }

    public Optional<ModuleModel> findByID(UUID moduleId) {
        Optional<ModuleModel> moduleRepositoryOptional = moduleRepository.findById(moduleId);
        if (moduleRepositoryOptional.isEmpty()) {
            throw new NotFoundException("Error: Module not found");
        }
        return moduleRepositoryOptional;
    }

    public Page<ModuleModel> findAllModulesIntoCourse(Specification<ModuleModel> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }
}
