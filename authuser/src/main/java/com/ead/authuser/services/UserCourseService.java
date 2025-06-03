package com.ead.authuser.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ead.authuser.repository.UserCourseRepository;

@Service
public class UserCourseService {

    @Autowired
    private UserCourseRepository userCourseRepository;
}
