package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.EnrollmentDto;
import com.example.spring_mvc.model.RatingDto;

import java.util.List;

public interface RatingService {
    List<RatingDto> listRatings(User user);

    Boolean addRating(Long courseId, RatingDto ratingDto, User user);

    void updateRating(Long ratingId, RatingDto ratingDto, User user);

    void deleteRating(Long ratingId, User user);

    RatingDto getRatingById(Long ratingId);
}
