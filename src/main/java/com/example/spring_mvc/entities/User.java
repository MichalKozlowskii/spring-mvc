package com.example.spring_mvc.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @NotBlank
    @Size(min = 3, max = 20)
    private String username;

    @NotNull
    @NotBlank
    @Size(min = 7)
    private String password;

    @NotNull
    @NotBlank
    @Size(min = 10)
    private String fullName;

    @Builder.Default
    @NotNull
    private Boolean enabled = true;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Builder.Default
    private Role role = Role.STUDENT;

    @OneToMany(mappedBy = "user")
    private List<Enrollment> enrollments = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Certificate> certificates = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + this.role.name());
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public enum Role {
        STUDENT,
        INSTRUCTOR,
        ADMIN
    }
}
