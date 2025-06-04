package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.CourseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CourseMapper {
    @Mapping(target = "instructor", ignore = true)
    Course courseDtoToCourse(CourseDto courseDto);

    @Mapping(source = "instructor", target = "instructorId")
    CourseDto courseToCourseDto(Course course);

    default Long map(User user) {
        return user != null ? user.getId() : null;
    }
}
