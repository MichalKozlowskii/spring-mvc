package com.example.spring_mvc.model.user;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.EnrollmentDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class UserViewDto {
    private Long id;
    private String username;
    private String fullName;
    private String role;
    private Boolean enabled;
}
