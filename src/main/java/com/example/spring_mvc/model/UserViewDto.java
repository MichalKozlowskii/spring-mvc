package com.example.spring_mvc.model;

import com.example.spring_mvc.entities.User;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserViewDto {
    private String userName;
    private String fullName;
    private User.Role role;
    private Boolean enabled;
}
