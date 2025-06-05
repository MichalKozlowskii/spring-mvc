package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByEndDateAfter(Date date);
}
