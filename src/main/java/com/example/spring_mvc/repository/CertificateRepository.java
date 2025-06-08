package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    @Query(value = """
    SELECT * FROM certificates
    WHERE user_id = :userId
    """, nativeQuery = true)
    List<Certificate> findByUserId(@Param("userId") Long userId);

    @Query(value = """
    SELECT c.* FROM certificates c
    JOIN courses co ON c.course_id = co.id
    WHERE co.instructor_id = :instructorId
    """, nativeQuery = true)
    List<Certificate> findByCourse_InstructorId(@Param("instructorId") Long instructorId);
    @Query("""
    SELECT CASE WHEN COUNT(c) > 0 THEN TRUE ELSE FALSE END
    FROM Certificate c
    WHERE c.user.id = :userId AND c.course.id = :courseId
    """)
    Boolean existsByUserIdAndCourseId(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
