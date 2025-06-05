package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.user.UserAuthDto;
import com.example.spring_mvc.model.user.UserViewDto;
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

    @Override
    public UserViewDto userToUserViewDto(User user) {
         return UserViewDto.builder()
                 .id(user.getId())
                 .username(user.getUsername())
                 .fullName(user.getFullName())
                 .enabled(user.getEnabled())
                 .role(user.getRole().toString())
                 .build();
    }
}
