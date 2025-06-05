package com.example.spring_mvc.controller;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.CourseDto;
import com.example.spring_mvc.service.CourseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public String listCourses(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("courses", courseService.listCourses(user));

        return "courses";
    }

    @GetMapping("/courses/add")
    public String createCourseForm(Model model) {
        model.addAttribute("course", Course.builder().build());

        return "addCourseForm";
    }

    @PostMapping("/courses/add")
    public String createCourse(@Valid @ModelAttribute("course") CourseDto courseDto,
                               @AuthenticationPrincipal User user,
                               BindingResult result,
                               Model model) {

        if (courseDto.getEndDate().isAfter(courseDto.getStartDate()) ||
                courseDto.getEndDate().isAfter(LocalDate.now())) {
            result.rejectValue("endDate", "Invalid date.");
        }

        if (courseDto.getStartDate().isAfter(LocalDate.now())) {
            result.rejectValue("startDate", "Invalid date.");
        }

        if (result.hasErrors()) {
            model.addAttribute("course", courseDto);
            return "addCourseForm";
        }

        if (!courseService.saveCourse(courseDto, user)) {
            return "redirect:/";
        }

        return "redirect:/courses";
    }
}
