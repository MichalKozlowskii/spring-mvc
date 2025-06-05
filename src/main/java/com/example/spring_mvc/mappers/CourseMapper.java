package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.model.CourseDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourseMapper {
    @Mapping(target = "instructor", ignore = true)
    Course courseDtoToCourse(CourseDto courseDto);
    CourseDto courseToCourseDto(Course course);
}
