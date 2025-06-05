package com.example.spring_mvc.model.course;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class ReiterationDto {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
}
