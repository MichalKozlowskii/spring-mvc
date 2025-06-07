package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Rating;
import com.example.spring_mvc.model.RatingDto;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, CourseMapper.class})
public interface RatingMapper {
    RatingDto ratingToRatingDto(Rating rating);
    Rating ratingDtoToRating(RatingDto ratingDto);
}
