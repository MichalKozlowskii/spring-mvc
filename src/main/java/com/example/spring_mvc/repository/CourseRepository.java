package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query(value = "select c.* from courses c where c.end_date > :date", nativeQuery = true)
    List<Course> findByEndDateAfter(@Param("date") Date date);
    @Query(value = "select c.* from courses c where c.instructor_id = :instructorId", nativeQuery = true)
    List<Course> findByInstructorId(@Param("instructorId") Long instructorId);

    @Query("""
    SELECT CASE
             WHEN COUNT(c) > 0 THEN true
             ELSE false
           END
    FROM Course c
    WHERE c.id = :courseId
      AND c.instructor.id = :instructorId
    """)
    Boolean existsByIdAndInstructorId(@Param("courseId") Long courseId, @Param("instructorId") Long instructorId);
}
