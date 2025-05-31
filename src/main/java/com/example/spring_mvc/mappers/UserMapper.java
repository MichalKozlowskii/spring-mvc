package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.UserAuthDto;

public interface UserMapper {
    User userAuthDtoToUser(UserAuthDto userAuthDto);
}
