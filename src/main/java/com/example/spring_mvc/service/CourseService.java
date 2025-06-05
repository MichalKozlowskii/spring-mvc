package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.CourseDto;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    Boolean saveCourse(CourseDto courseDto, User user);
    void editCourse(Long courseId, CourseDto courseDto, User user);
    List<CourseDto> listCourses(User user);
    Boolean canUpdate(User user, Long courseId);
    Optional<CourseDto> getCourse(Long courseId);
}
