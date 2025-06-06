package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.Enrollment;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.mappers.EnrollmentMapper;
import com.example.spring_mvc.model.EnrollmentDto;
import com.example.spring_mvc.repository.CourseRepository;
import com.example.spring_mvc.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentMapper enrollmentMapper;
    @Override
    public Boolean enrollStudent(User user, Long courseId) {
        if (user.getRole() != User.Role.STUDENT) return false;

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return false;
        if (course.getStartDate().before(Date.valueOf(LocalDate.now()))) return false;


        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .status(Enrollment.EnrollmentStatus.IN_PROGRESS)
                .user(user)
                .build();

        enrollmentRepository.save(enrollment);

        return true;
    }

    @Override
    public Boolean isEnrolled(User user, Long courseId) {
        return enrollmentRepository.existsByCourseIdAndUser(courseId, user);
    }

    @Override
    public List<EnrollmentDto> listEnrollments(User user) {
        List<Enrollment> enrollments = new ArrayList<>();

        switch (user.getRole()) {
            case STUDENT -> enrollments = enrollmentRepository.findByUser(user);
            case ADMIN -> enrollments = enrollmentRepository.findAll();
            case INSTRUCTOR -> enrollments = enrollmentRepository.findByCourse_Instructor(user);
        }

        return enrollments.stream().map(enrollmentMapper::enrollmentToEnrollmentDto).toList();
    }
}
