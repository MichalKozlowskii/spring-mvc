package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.CertificateDto;
import com.example.spring_mvc.model.EnrollmentDto;

import java.util.List;

public interface CertificateService {
    List<CertificateDto> listCertificates(User user);
    List<EnrollmentDto> listFinishedEnrollmentsNotCertified(User user);
    void issueCertificate(Long enrollmentId, User user);
}
