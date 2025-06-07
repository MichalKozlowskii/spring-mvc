package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.Enrollment;
import com.example.spring_mvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Boolean existsByCourseIdAndUserAndStatusNot(Long courseId, User user, Enrollment.EnrollmentStatus status);
    List<Enrollment> findByUser(User user);
    List<Enrollment> findByCourse_Instructor(User user);
    List<Enrollment> findByCourse_InstructorAndStatus(User user, Enrollment.EnrollmentStatus status);
    List<Enrollment> findByStatus(Enrollment.EnrollmentStatus status);
}
