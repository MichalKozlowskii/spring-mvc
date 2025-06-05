package com.example.spring_mvc.model.user;

import com.example.spring_mvc.entities.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserViewDto {
    private Long id;
    private String username;
    private String fullName;
    private String role;
    private Boolean enabled;

}
