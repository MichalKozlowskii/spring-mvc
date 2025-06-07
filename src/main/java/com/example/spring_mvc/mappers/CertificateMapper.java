package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.Certificate;
import com.example.spring_mvc.model.CertificateDto;
import org.mapstruct.Mapper;

@Mapper(uses = {UserMapper.class, CourseMapper.class})
public interface CertificateMapper {
    CertificateDto certificateToCertificateDto(Certificate certificate);
}
