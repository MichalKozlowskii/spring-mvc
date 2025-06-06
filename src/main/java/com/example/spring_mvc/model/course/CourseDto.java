package com.example.spring_mvc.model.course;

import com.example.spring_mvc.model.user.UserViewDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class CourseDto {
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private String category;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer hoursPerWeek;

    @Builder.Default
    private Integer iteration = 1;

    private UserViewDto instructor;

    // Lista enrollments

    // Lista ratings
}
