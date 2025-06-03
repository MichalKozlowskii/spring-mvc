package com.example.spring_mvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAuthDto {
    @NotNull(message = "Username has to be specified.")
    @NotBlank(message = "Username must not be blank.")
    @Size(min = 3, max = 20)
    private String username;

    @NotNull(message = "Full name has to be specified.")
    @NotBlank(message = "Full name must not be blank.")
    @Size(min = 10, max = 40)
    private String fullName;

    @NotNull(message = "Password has to be specified.")
    @NotBlank(message = "Password must not be blank.")
    @Size(min = 7, max = 40)
    private String password;
}
