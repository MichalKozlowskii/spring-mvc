package com.example.spring_mvc.mappers;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.user.UserAuthDto;
import com.example.spring_mvc.model.user.UserViewDto;

public interface UserMapper {
    User userAuthDtoToUser(UserAuthDto userAuthDto);
    UserViewDto userToUserViewDto(User user);
}
