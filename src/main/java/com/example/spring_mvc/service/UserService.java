package com.example.spring_mvc.service;

import com.example.spring_mvc.model.user.UserAuthDto;

public interface UserService {
    Boolean registerUser(UserAuthDto userAuthDto);
}
