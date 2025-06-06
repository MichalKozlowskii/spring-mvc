package com.example.spring_mvc.model;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.model.course.CourseDto;
import com.example.spring_mvc.model.user.UserViewDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class EnrollmentDto {
    private Long id;
    private UserViewDto user;
    private CourseDto course;
    private String status;
    private LocalDate enrollmentDate;
}
