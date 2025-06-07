package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.Certificate;
import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.Enrollment;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.mappers.CertificateMapper;
import com.example.spring_mvc.mappers.EnrollmentMapper;
import com.example.spring_mvc.model.CertificateDto;
import com.example.spring_mvc.model.EnrollmentDto;
import com.example.spring_mvc.repository.CertificateRepository;
import com.example.spring_mvc.repository.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateMapper certificateMapper;
    private final CertificateRepository certificateRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    @Override
    public List<CertificateDto> listCertificates(User user) {
        List<Certificate> certificates = new ArrayList<>();

        switch (user.getRole()) {
            case STUDENT -> certificates = certificateRepository.findByUser(user);
            case INSTRUCTOR -> certificates = certificateRepository.findByCourse_Instructor(user);
            case ADMIN -> certificates = certificateRepository.findAll();
        }

        return certificates.stream().map(certificateMapper::certificateToCertificateDto).toList();
    }

    @Override
    public List<EnrollmentDto> listFinishedEnrollmentsNotCertified(User issuer) {
        List<Enrollment> enrollments = new ArrayList<>();

        switch (issuer.getRole()) {
            case ADMIN ->
                    enrollments = enrollmentRepository.findByStatus(Enrollment.EnrollmentStatus.COMPLETED);
            case INSTRUCTOR ->
                    enrollments = enrollmentRepository
                            .findByCourse_InstructorAndStatus(issuer, Enrollment.EnrollmentStatus.COMPLETED);
        }

        return enrollments.stream()
                .filter(e -> !certificateRepository.existsByUserAndCourse(e.getUser(), e.getCourse()))
                .map(enrollmentMapper::enrollmentToEnrollmentDto)
                .toList();
    }

    @Override
    public void issueCertificate(Long enrollmentId, User issuer) {
        if (issuer.getRole() == User.Role.STUDENT) return;

        Enrollment enrollment = enrollmentRepository.findById(enrollmentId).orElse(null);
        if (enrollment == null) return;

        Course course = enrollment.getCourse();
        if (issuer.getRole() == User.Role.INSTRUCTOR && !issuer.getId().equals(course.getInstructor().getId())) return;
        if (certificateRepository.existsByUserAndCourse(enrollment.getUser(), course)) return;

        Certificate certificate = Certificate.builder()
                .user(enrollment.getUser())
                .course(course)
                .build();

        certificateRepository.save(certificate);
    }

}
