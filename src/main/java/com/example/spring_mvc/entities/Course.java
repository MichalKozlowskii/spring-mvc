package com.example.spring_mvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    private String title;

    @NotNull
    @NotBlank
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    @NotNull
    private Integer hoursPerWeek;

    @ManyToOne
    @NotNull
    private User instructor;

    @OneToMany(mappedBy = "course")
    private List<Enrollment> enrollments;

    @NotNull
    private Integer iteration = 1;

    public enum Category {
        WEB_DEV,
        CYBER_SECURITY,
        CLOUD_DEVOPS
    }
}
