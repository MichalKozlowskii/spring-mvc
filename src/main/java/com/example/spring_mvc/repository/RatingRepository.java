package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.Rating;
import com.example.spring_mvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    @Query(value = "SELECT * FROM ratings WHERE user_id = :userId", nativeQuery = true)
    List<Rating> findRatingsByUserId(@Param("userId") Long userId);
    @Query(value = """
    SELECT r.* 
    FROM ratings r
    JOIN courses c ON r.course_id = c.id
    WHERE c.instructor_id = :instructorId
    """, nativeQuery = true)
    List<Rating> findRatingsByCourse_InstructorId(@Param("instructorId") Long instructorId);
    @Query("""
    SELECT CASE
        WHEN COUNT(r) > 0 THEN TRUE
        ELSE FALSE
    END
    FROM Rating r
    WHERE r.course.id = :courseId
      AND r.user.id = :userId
    """)
    Boolean existsByCourseIdAndUserId(@Param("courseId") Long courseId, @Param("userId") Long userId);
    @Query(value = "SELECT AVG(stars) FROM ratings WHERE course_id = :courseId", nativeQuery = true)
    Double findAverageStarsByCourseId(@Param("courseId") Long courseId);
    @Query(value = "SELECT * FROM ratings WHERE course_id = :courseId", nativeQuery = true)
    List<Rating> findRatingsByCourseId(@Param("courseId") Long courseId);
}
