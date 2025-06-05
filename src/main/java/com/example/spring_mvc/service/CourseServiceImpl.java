package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.mappers.CourseMapper;
import com.example.spring_mvc.model.course.CourseDto;
import com.example.spring_mvc.model.course.ReiterationDto;
import com.example.spring_mvc.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
    public void editCourse(Long courseId, CourseDto courseDto) {
        Course course = courseRepository.findById(courseId).get(); // always present, checked in controller

        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());
        course.setCategory(Course.Category.valueOf(courseDto.getCategory()));
        course.setStartDate(Date.valueOf(courseDto.getStartDate()));
        course.setEndDate(Date.valueOf(courseDto.getEndDate()));
        course.setHoursPerWeek(courseDto.getHoursPerWeek());

        courseRepository.save(course);
    }

    @Override
    public Boolean deleteCourse(Long courseId, User user) {
        if (!user.getEnabled()) return false;
        if (user.getRole() == User.Role.STUDENT) return false;

        Course foundCourse = courseRepository.findById(courseId).orElse(null);
        if (foundCourse == null) return false;

        if (user.getRole() == User.Role.INSTRUCTOR && !user.getId().equals(foundCourse.getInstructor().getId()))
            return false;

        if (!foundCourse.getEnrollments().isEmpty()) return false;

        courseRepository.delete(foundCourse);

        return true;
    }

    @Override
    public Boolean reiterateCourse(Long courseId, ReiterationDto reiterationDto) {
        Course foundCourse = courseRepository.findById(courseId).get();

        if (Date.valueOf(LocalDate.now()).before(foundCourse.getEndDate())) return false;

        foundCourse.setStartDate(Date.valueOf(reiterationDto.getStartDate()));
        foundCourse.setEndDate(Date.valueOf(reiterationDto.getEndDate()));
        foundCourse.setIteration(foundCourse.getIteration() + 1);

        courseRepository.save(foundCourse);

        return true;
    }

    @Override
    public List<CourseDto> listCourses(User user) {
        List<Course> courses;

        if (user.getRole() == User.Role.ADMIN) {
            courses = courseRepository.findAll();
        } else if (user.getRole() == User.Role.INSTRUCTOR) {
            courses = courseRepository.findByInstructor(user);
        } else {
            courses = courseRepository.findByEndDateAfter(Date.valueOf(LocalDate.now()));
        }

        return courses.stream()
                .map(courseMapper::courseToCourseDto)
                .toList();
    }

    @Override
    public Boolean canUpdate(User user, Long courseId) {
        if (!user.getEnabled()) return false;
        if (user.getRole() == User.Role.ADMIN) return true;

        return user.getRole() == User.Role.INSTRUCTOR && courseRepository.existsByIdAndInstructor(courseId, user);
    }

    @Override
    public Optional<CourseDto> getCourse(Long courseId) {
        return courseRepository.findById(courseId)
                .map(courseMapper::courseToCourseDto);
    }
}
