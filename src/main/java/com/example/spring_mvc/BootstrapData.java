package com.example.spring_mvc;

import com.example.spring_mvc.entities.*;
import com.example.spring_mvc.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;

@Component
@RequiredArgsConstructor
@Transactional
public class BootstrapData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final RatingRepository ratingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CertificateRepository certificateRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = userRepository.saveAndFlush(User.builder()
                .userName("user")
                .fullName("user user user")
                .password("fadfafafsa")
                .role(User.Role.STUDENT)
                .certificates(new ArrayList<>())
                .enrollments(new ArrayList<>())
                .ratings(new ArrayList<>())
                .build());

        User instructor = userRepository.saveAndFlush(User.builder()
                .userName("1321341")
                .fullName("fafsafj afsfsaf12321")
                .password("Dsadsadsad")
                .role(User.Role.INSTRUCTOR)
                .build());

        Course course = courseRepository.saveAndFlush(Course.builder()
                .title("KURS FAJNY")
                .description("FFAJFIOJAFOIJA")
                .category(Course.Category.BACKEND)
                .startDate(Date.valueOf(LocalDate.now()))
                .endDate(Date.valueOf(LocalDate.now()))
                .hoursPerWeek(10)
                .instructor(instructor)
                .iteration(1)
                        .ratings(new ArrayList<>())
                .build());

        Enrollment enrollment = enrollmentRepository.saveAndFlush(Enrollment.builder()
                .course(course)
                .enrollmentDate(Date.valueOf(LocalDate.now()))
                .status(Enrollment.EnrollmentStatus.IN_PROGRESS)
                .user(user)
                .build());

        Rating rating = ratingRepository.saveAndFlush(Rating.builder()
                .comment("slabe")
                .stars(1)
                .course(course)
                .user(user)
                .build());

        Certificate certificate = certificateRepository.saveAndFlush(Certificate.builder()
                .course(course)
                .user(user)
                .build());

        user.getEnrollments().add(enrollment);
        user.getCertificates().add(certificate);
        user.getRatings().add(rating);
        course.getRatings().add(rating);

        user = userRepository.findAll().getFirst();

        course = courseRepository.findAll().getFirst();

        System.out.println(course.getRatings());

        System.out.println(user.getUsername() + " " + user.getFullName() + " " + user.getPassword());

        System.out.println(user.getEnrollments());
        System.out.println(user.getCertificates());
        System.out.println(user.getRatings());
    }
}
