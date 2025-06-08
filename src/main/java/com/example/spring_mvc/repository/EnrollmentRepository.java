package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    @Query("""
    SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END
    FROM Enrollment e
    WHERE e.course.id = :courseId
      AND e.user.id = :userId
      AND e.status != :status
    """)
    Boolean existsByCourseIdAndUserIdAndStatusNot(@Param("courseId") Long courseId,
                                                  @Param("userId") Long userId,
                                                  @Param("status") Enrollment.EnrollmentStatus status);
    @Query(value = """
    SELECT e.*
    FROM enrollments e
    WHERE e.user_id = :userId
    """, nativeQuery = true)
    List<Enrollment> findByUserId(@Param("userId") Long userId);
    @Query(value = """
    SELECT e.* 
    FROM enrollments e
    JOIN courses c ON c.id = e.course_id
    JOIN users u ON u.id = c.instructor_id
    WHERE u.id = :instructorId
    """, nativeQuery = true)
    List<Enrollment> findByCourse_InstructorId(@Param("instructorId") Long instructorId);
    @Query(value = """
    SELECT e.* FROM enrollments e
    JOIN courses c ON c.id = e.course_id
    JOIN users u ON u.id = c.instructor_id
    WHERE u.id = :instructorId AND e.status = :status
    """, nativeQuery = true)
    List<Enrollment> findByCourse_InstructorIdAndStatus(@Param("instructorId") Long instructorId,
                                                        @Param("status") Enrollment.EnrollmentStatus status);
    @Query(value = """
    SELECT e.*
    FROM enrollments e
    WHERE e.status = :status
    """, nativeQuery = true)
    List<Enrollment> findByStatus(Enrollment.EnrollmentStatus status);

    @Query(value = """
    SELECT * FROM enrollments 
    WHERE course_id = :courseId 
      AND user_id = :userId 
    LIMIT 1
    """, nativeQuery = true)
    Enrollment findByCourseIdAndUserId(@Param("courseId") Long courseId, @Param("userId") Long userId);
}
