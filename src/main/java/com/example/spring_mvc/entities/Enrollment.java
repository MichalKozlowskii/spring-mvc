package com.example.spring_mvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "enrollments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Course course;

    @NotNull
    private EnrollmentStatus status;

    @CreationTimestamp
    @Column(updatable = false)
    private Date enrollmentDate;

    public enum EnrollmentStatus {
        IN_PROGRESS,
        COMPLETED,
        RESIGNED
    }
}
