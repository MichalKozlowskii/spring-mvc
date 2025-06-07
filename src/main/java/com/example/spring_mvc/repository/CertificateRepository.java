package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Certificate;
import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByUser(User user);
    List<Certificate> findByCourse_Instructor(User instructor);
    Boolean existsByUserAndCourse(User user, Course course);
}
