package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
