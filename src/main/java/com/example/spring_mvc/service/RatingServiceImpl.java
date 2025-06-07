package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.Enrollment;
import com.example.spring_mvc.entities.Rating;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.mappers.RatingMapper;
import com.example.spring_mvc.model.RatingDto;
import com.example.spring_mvc.repository.CourseRepository;
import com.example.spring_mvc.repository.EnrollmentRepository;
import com.example.spring_mvc.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingMapper ratingMapper;
    private final RatingRepository ratingRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<RatingDto> listRatings(User user) {
        List<Rating> ratings = new ArrayList<>();

        switch (user.getRole()) {
            case STUDENT -> ratings = ratingRepository.findRatingsByUser(user);
            case INSTRUCTOR -> ratings = ratingRepository.findRatingsByCourse_Instructor(user);
            case ADMIN -> ratings = ratingRepository.findAll();
        }

        return ratings.stream().map(ratingMapper::ratingToRatingDto).toList();
    }

    @Override
    public Boolean addRating(Long courseId, RatingDto ratingDto, User user) {
        if (user.getRole() != User.Role.STUDENT) return false;

        Course course = courseRepository.findById(courseId).orElse(null);
        if (course == null) return false;
        if (ratingRepository.existsByCourseAndUser(course, user)) return false;

        Enrollment enrollment = enrollmentRepository.findByCourseAndUser(course, user);
        if (enrollment == null) return false;
        if (enrollment.getStatus() == Enrollment.EnrollmentStatus.IN_PROGRESS) return false;

        Rating rating = Rating.builder()
                .stars(ratingDto.getStars())
                .comment(ratingDto.getComment())
                .course(course)
                .user(user)
                .build();

        ratingRepository.save(rating);

        return true;
    }

    @Override
    public void updateRatingById(Long ratingId, RatingDto ratingDto, User user) {
        Rating rating =  ratingRepository.findById(ratingId).orElse(null);
        if (rating == null) return;
        if (!rating.getUser().getId().equals(user.getId())) return;

        rating.setStars(ratingDto.getStars());
        rating.setComment(ratingDto.getComment());

        ratingRepository.save(rating);
    }

    @Override
    public void deleteRatingById(Long ratingId, User user) {
        Rating rating = ratingRepository.findById(ratingId).orElse(null);
        if (rating == null) return;
        if (user.getRole() == User.Role.STUDENT && !user.getId().equals(rating.getUser().getId())) return;

        Course course = rating.getCourse();
        if (user.getRole() == User.Role.INSTRUCTOR && !user.getId().equals(course.getInstructor().getId())) return;

        ratingRepository.delete(rating);
    }

    @Override
    public RatingDto getRatingById(Long ratingId) {
        return ratingRepository.findById(ratingId)
                .map(ratingMapper::ratingToRatingDto)
                .orElse(null);
    }

    @Override
    public Double getAvgRatingOfCourse(Long courseId) {
        return ratingRepository.findAverageStarsByCourseId(courseId);
    }

    @Override
    public List<RatingDto> listRatingsOfCourse(Long courseId) {
        return ratingRepository.findRatingsByCourseId(courseId).stream()
                .map(ratingMapper::ratingToRatingDto)
                .toList();
    }

}
