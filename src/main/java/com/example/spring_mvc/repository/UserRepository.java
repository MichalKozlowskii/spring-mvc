package com.example.spring_mvc.repository;

import com.example.spring_mvc.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
