package com.example.spring_mvc.model;

import com.example.spring_mvc.model.course.CourseDto;
import com.example.spring_mvc.model.user.UserViewDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CertificateDto {
    private Long id;
    private CourseDto course;
    private UserViewDto user;
    private LocalDate date;
}
