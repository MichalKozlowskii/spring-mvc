package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}
