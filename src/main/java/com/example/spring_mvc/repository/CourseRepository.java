package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
