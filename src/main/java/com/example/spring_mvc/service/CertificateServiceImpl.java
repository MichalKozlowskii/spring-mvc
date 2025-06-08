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
            case STUDENT -> certificates = certificateRepository.findByUserId(user.getId());
            case INSTRUCTOR -> certificates = certificateRepository.findByCourse_InstructorId(user.getId());
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
                            .findByCourse_InstructorIdAndStatus(issuer.getId(), Enrollment.EnrollmentStatus.COMPLETED);
        }

        return enrollments.stream()
                .filter(e -> !certificateRepository.existsByUserIdAndCourseId(e.getUser().getId(), e.getCourse().getId()))
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
        if (certificateRepository.existsByUserIdAndCourseId(enrollment.getUser().getId(), course.getId())) return;

        Certificate certificate = Certificate.builder()
                .user(enrollment.getUser())
                .course(course)
                .build();

        certificateRepository.save(certificate);
    }

    @Override
    public void revokeCertificate(Long certificateId, User issuer) {
        if (issuer.getRole() == User.Role.STUDENT) return;

        Certificate certificate = certificateRepository.findById(certificateId).orElse(null);
        if (certificate == null) return;

        Course course = certificate.getCourse();
        if (issuer.getRole() == User.Role.INSTRUCTOR && !issuer.getId().equals(course.getInstructor().getId())) return;

        certificateRepository.delete(certificate);
    }

}
