package com.example.spring_mvc.service;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.CertificateDto;

import java.util.List;

public interface CertificateService {
    List<CertificateDto> listCertificates(User user);
}
