package com.example.spring_mvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class CourseDTO {
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
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Integer hoursPerWeek;

    @Builder.Default
    private Integer iteration = 1;

    private Long instructorId;

    // Lista enrollments

    // Lista ratings
}
