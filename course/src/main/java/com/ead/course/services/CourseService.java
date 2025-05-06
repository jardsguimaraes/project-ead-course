package com.ead.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
