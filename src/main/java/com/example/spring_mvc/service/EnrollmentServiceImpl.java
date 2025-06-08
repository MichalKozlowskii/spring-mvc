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
    public void enrollStudent(User user, Long courseId) {
        if (user.getRole() != User.Role.STUDENT) return;
        if (enrollmentRepository.existsByCourseIdAndUserIdAndStatusNot(courseId, user.getId(), Enrollment.EnrollmentStatus.RESIGNED)) {
            return;
        }

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return;
        if (course.getStartDate().before(Date.valueOf(LocalDate.now()))) return;


        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .status(Enrollment.EnrollmentStatus.IN_PROGRESS)
                .user(user)
                .build();

        enrollmentRepository.save(enrollment);
    }

    @Override
    public List<EnrollmentDto> listEnrollments(User user) {
        List<Enrollment> enrollments = new ArrayList<>();

        switch (user.getRole()) {
            case STUDENT -> enrollments = enrollmentRepository.findByUserId(user.getId());
            case ADMIN -> enrollments = enrollmentRepository.findAll();
            case INSTRUCTOR -> enrollments = enrollmentRepository.findByCourse_InstructorId(user.getId());
        }

        return enrollments.stream().map(enrollmentMapper::enrollmentToEnrollmentDto).toList();
    }

    @Override
    public void updateStatus(Long enrollmentId, String status, User user) {
        if (user.getRole() == User.Role.STUDENT) return;

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);
        if (enrollment == null) return;

        User courseInstructor = enrollment.getCourse().getInstructor();
        if (user.getRole() == User.Role.INSTRUCTOR && !user.getId().equals(courseInstructor.getId())) return;

        enrollment.setStatus(Enrollment.EnrollmentStatus.valueOf(status));

        enrollmentRepository.save(enrollment);
    }
}
