package com.example.spring_mvc.model;

import com.example.spring_mvc.model.course.CourseDto;
import com.example.spring_mvc.model.user.UserViewDto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RatingDto {
    private Long id;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer stars;
    @NotBlank
    private String comment;

    private UserViewDto user;
    private CourseDto courseDto;
}
