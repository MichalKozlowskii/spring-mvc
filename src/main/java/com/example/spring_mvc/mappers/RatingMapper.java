package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Rating;
import com.example.spring_mvc.model.RatingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {UserMapper.class, CourseMapper.class})
public interface RatingMapper {
    @Mapping(source = "course", target = "course")
    RatingDto ratingToRatingDto(Rating rating);
}
