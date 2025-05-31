package com.example.spring_mvc.service;

import com.example.spring_mvc.model.UserAuthDto;

public interface UserService {
    Boolean registerUser(UserAuthDto userAuthDto);
}
