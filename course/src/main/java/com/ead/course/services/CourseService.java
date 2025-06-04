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

import com.ead.course.dtos.CourseRecordDto;
import com.ead.course.exceptions.NotFoundException;
import com.ead.course.model.CourseModel;
import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.CourseReporitory;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;

@Service
public class CourseService {

    @Autowired
    private CourseReporitory courseReporitory;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Transactional
    public void delete(CourseModel courseModel) {
        List<ModuleModel> moduleModelsList = moduleRepository.findAllModulesIntoCourse(courseModel.getCourseId());

        if (!moduleModelsList.isEmpty()) {
            for (ModuleModel module : moduleModelsList) {
                List<LessonModel> lessonModuleList = lessonRepository.findAllLessonsIntoModule(module.getModuleId());

                if (!lessonModuleList.isEmpty()) {
                    lessonRepository.deleteAll(lessonModuleList);
                }
            }
            moduleRepository.deleteAll(moduleModelsList);
        }

        courseReporitory.delete(courseModel);
    }

    public CourseModel save(CourseRecordDto courseRecordDto) {
        var courseModel = new CourseModel();
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseModel.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));

        return courseReporitory.save(courseModel);
    }

    public Optional<CourseModel> getCourseFindById(UUID courseId) {
        Optional<CourseModel> courseModelOptinal = courseReporitory.findById(courseId);

        if (courseModelOptinal.isEmpty()) {
            throw new NotFoundException("Error: Course not found");
        }

        return courseModelOptinal;
    }

    public CourseModel update(CourseRecordDto courseRecordDto, CourseModel courseModel) {
        BeanUtils.copyProperties(courseRecordDto, courseModel);
        courseModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseReporitory.save(courseModel);
    }

    public boolean existsByName(String name) {
        return courseReporitory.existsByName(name);
    }

    public Page<CourseModel> getFindAll(Specification<CourseModel> spec, Pageable pageable) {
        return courseReporitory.findAll(spec, pageable);
    }
}
