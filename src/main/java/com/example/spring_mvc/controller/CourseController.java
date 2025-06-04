package com.example.spring_mvc.controller;

import com.example.spring_mvc.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
}
