package com.ead.course.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.course.repository.CourseUserRepository;

@Service
public class CourseUserService {

    @Autowired
    private CourseUserRepository courseUserRepository;
}
