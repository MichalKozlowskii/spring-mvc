package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.mappers.CourseMapper;
import com.example.spring_mvc.model.CourseDto;
import com.example.spring_mvc.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;

    @Override
    public Boolean saveCourse(CourseDto courseDto, User user) {
        if (!user.getEnabled() || user.getRole() != User.Role.INSTRUCTOR) return false;

        Course course = courseMapper.courseDtoToCourse(courseDto);
        course.setInstructor(user);
        course.setIteration(1);

        courseRepository.save(course);

        return true;
    }

    @Override
    public List<CourseDto> listCourses(User user) {
        List<Course> courses;

        if (user.getRole() == User.Role.ADMIN) {
            courses = courseRepository.findAll();
        } else {
            courses = courseRepository.findByEndDateAfter(Date.valueOf(LocalDate.now()));
        }

        return courses.stream()
                .map(courseMapper::courseToCourseDto)
                .toList();
    }
}
