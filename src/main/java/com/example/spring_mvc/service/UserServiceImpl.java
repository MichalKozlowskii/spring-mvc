package com.example.spring_mvc.service;

import com.example.spring_mvc.mappers.UserMapper;
import com.example.spring_mvc.model.user.UserAuthDto;
import com.example.spring_mvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Boolean registerUser(UserAuthDto userAuthDto) {
        if (userRepository.findByUsername(userAuthDto.getUsername()).isPresent()) {
            return false;
        }

        userRepository.save(userMapper.userAuthDtoToUser(userAuthDto));

        return true;
    }
}
