package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.EnrollmentDto;

import java.util.List;

public interface EnrollmentService {
    void enrollStudent(User user, Long courseId);
    List<EnrollmentDto> listEnrollments(User user);
    void updateStatus(Long enrollmentId, String status, User user);
}
