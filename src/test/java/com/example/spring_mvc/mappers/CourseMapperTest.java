package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.CourseDto;
import com.example.spring_mvc.model.UserViewDto;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class CourseMapperTest {

    private final CourseMapper mapper = new CourseMapperImpl(new UserMapperImpl(new BCryptPasswordEncoder()));
    @Test
    void shouldMapDtoToEntity() {
        CourseDto dto = CourseDto.builder()
                .id(1L)
                .title("Spring Boot 101")
                .description("Intro course")
                .category("WEB_DEV")
                .startDate(new Date())
                .endDate(new Date())
                .hoursPerWeek(6)
                .iteration(2)
                .build();

        Course course = mapper.courseDtoToCourse(dto);

        assertThat(course.getTitle()).isEqualTo(dto.getTitle());
        assertThat(course.getCategory().name()).isEqualTo(dto.getCategory());
    }

    @Test
    void shouldMapEntityToDto() {
        User instructor = User.builder()
                .id(42L)
                .fullName("adsadas")
                .role(User.Role.STUDENT)
                .build();

        Course course = Course.builder()
                .id(1L)
                .title("Spring Boot 101")
                .description("Intro course")
                .category(Course.Category.WEB_DEV)
                .startDate(new Date())
                .endDate(new Date())
                .hoursPerWeek(6)
                .iteration(2)
                .instructor(instructor)
                .build();

        CourseDto dto = mapper.courseToCourseDto(course);

        UserViewDto instructorDto = dto.getInstructor();

        assertThat(dto.getInstructor().getId()).isEqualTo(42L);
        assertThat(instructorDto.getFullName()).isEqualTo(instructor.getFullName());
        assertThat(instructorDto.getRole()).isEqualTo(instructor.getRole().toString());

        assertThat(dto.getCategory()).isEqualTo("WEB_DEV");
        assertThat(dto.getTitle()).isEqualTo(course.getTitle());
    }
}
