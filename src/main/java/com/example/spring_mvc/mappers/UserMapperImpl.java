package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.UserAuthDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder passwordEncoder;

    @Override
    public User userAuthDtoToUser(UserAuthDto userAuthDto) {
        return User.builder()
                .username(userAuthDto.getUsername())
                .password(passwordEncoder.encode(userAuthDto.getPassword()))
                .fullName(userAuthDto.getFullName())
                .build();
    }
}
