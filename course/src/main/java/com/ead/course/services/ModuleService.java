package com.ead.course.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
