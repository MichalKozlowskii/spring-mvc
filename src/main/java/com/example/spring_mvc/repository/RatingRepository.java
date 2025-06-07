package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.Rating;
import com.example.spring_mvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findRatingsByUser(User user);
    List<Rating> findRatingsByCourse_Instructor(User instructor);
    Boolean existsByCourseAndUser(Course course, User user);
}
