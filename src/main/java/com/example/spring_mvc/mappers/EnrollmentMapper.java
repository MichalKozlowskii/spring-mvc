package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Enrollment;
import com.example.spring_mvc.model.EnrollmentDto;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, CourseMapper.class})
public interface EnrollmentMapper {
    EnrollmentDto enrollmentToEnrollmentDto(Enrollment enrollment);
}
