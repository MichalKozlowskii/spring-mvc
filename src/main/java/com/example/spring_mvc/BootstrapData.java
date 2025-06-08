package com.example.spring_mvc;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CertificateRepository certificateRepository;
    private final RatingRepository ratingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        User student1 = userRepository.save(User.builder()
                .username("student1")
                .fullName("Marek Towarek")
                .password(passwordEncoder.encode("password"))
                .role(User.Role.STUDENT)
                .enabled(true)
                .build());

        User student2 = userRepository.save(User.builder()
                .username("student2")
                .fullName("Marek Kanarek")
                .password(passwordEncoder.encode("password"))
                .role(User.Role.STUDENT)
                .enabled(true)
                .build());

        User instructor1 = userRepository.save(User.builder()
                .username("instructor1")
                .fullName("Jan Nowak")
                .password(passwordEncoder.encode("password"))
                .role(User.Role.INSTRUCTOR)
                .enabled(true)
                .build());

        User instructor2 = userRepository.save(User.builder()
                .username("instructor2")
                .fullName("Karol Kowalski")
                .password(passwordEncoder.encode("password"))
                .role(User.Role.INSTRUCTOR)
                .enabled(true)
                .build());

        User admin = userRepository.save(User.builder()
                .username("admin")
                .fullName("Mariusz Pudzianowski")
                .password(passwordEncoder.encode("password"))
                .role(User.Role.ADMIN)
                .enabled(true)
                .build());

        Course course1Instructor1 = courseRepository.save(Course.builder()
                .title("spring boot basics")
                .description("Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial")
                .category(Course.Category.WEB_DEV)
                .startDate(Date.valueOf(LocalDate.now().minusDays(40)))
                .endDate(Date.valueOf(LocalDate.now().minusDays(1)))
                .hoursPerWeek(8)
                .iteration(1)
                .instructor(instructor1)
                .build());


        Course course2Instructor1 = courseRepository.save(Course.builder()
                .title("Next.JS basics")
                .description("Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial")
                .category(Course.Category.WEB_DEV)
                .startDate(Date.valueOf(LocalDate.now().minusDays(40)))
                .endDate(Date.valueOf(LocalDate.now().plusDays(30)))
                .hoursPerWeek(10)
                .iteration(1)
                .instructor(instructor1)
                .build());

        Course course3Instructor1 = courseRepository.save(Course.builder()
                .title("spring boot with microservices")
                .description("Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial")
                .category(Course.Category.WEB_DEV)
                .startDate(Date.valueOf(LocalDate.now().plusDays(10)))
                .endDate(Date.valueOf(LocalDate.now().plusDays(90)))
                .hoursPerWeek(5)
                .iteration(1)
                .instructor(instructor1)
                .build());

        Course course1Instructor2 = courseRepository.save(Course.builder()
                .title("Azure introduction")
                .description("Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial Fajny tutorial")
                .category(Course.Category.CLOUD_DEVOPS)
                .startDate(Date.valueOf(LocalDate.now().plusDays(20)))
                .endDate(Date.valueOf(LocalDate.now().plusDays(100)))
                .hoursPerWeek(10)
                .iteration(1)
                .instructor(instructor1)
                .build());
    }
}
