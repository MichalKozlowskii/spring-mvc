package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.Certificate;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.mappers.CertificateMapper;
import com.example.spring_mvc.model.CertificateDto;
import com.example.spring_mvc.repository.CertificateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CertificateServiceImpl implements CertificateService {
    private final CertificateMapper certificateMapper;
    private final CertificateRepository certificateRepository;

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
}
