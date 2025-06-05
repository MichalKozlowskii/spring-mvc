package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.UserAuthDto;
import com.example.spring_mvc.model.UserViewDto;

public interface UserMapper {
    User userAuthDtoToUser(UserAuthDto userAuthDto);
    UserViewDto userToUserViewDto(User user);
}
