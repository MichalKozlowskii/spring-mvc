package com.example.spring_mvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserAuthDto {
    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String userName;

    @NotNull
    @NotBlank
    @Size(min = 10)
    private String fullName;

    @NotNull
    @NotBlank
    @Size(min = 7)
    private String password;
}
