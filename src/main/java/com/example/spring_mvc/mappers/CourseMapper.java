package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.model.course.CourseDto;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Mapper(uses = UserMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CourseMapper {
    @Mapping(target = "instructor", ignore = true)
    Course courseDtoToCourse(CourseDto courseDto);
    CourseDto courseToCourseDto(Course course);

    default Date map(LocalDate date) {
        return date == null ? null : Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    default LocalDate map(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
