package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.CourseDto;

import java.util.List;

public interface CourseService {
    Boolean saveCourse(CourseDto courseDto, User user);
    List<CourseDto> listCourses();
}
