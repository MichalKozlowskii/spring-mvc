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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/courses")
    public String listCourses(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("courses", courseService.listCourses(user));
        model.addAttribute("userId", user.getId());

        return "courses";
    }

    @GetMapping("/courses/add")
    public String creationForm(Model model) {
        model.addAttribute("course", Course.builder().build());
        model.addAttribute("method", "post");
        model.addAttribute("actionUrl", "/courses/add");
        model.addAttribute("buttonText", "Create");

        return "courseForm";
    }

    @PostMapping("/courses/add")
    public String createCourse(@Valid @ModelAttribute("course") CourseDto courseDto,
                               @AuthenticationPrincipal User user,
                               BindingResult result,
                               Model model) {

        verifyDates(courseDto, result);

        if (result.hasErrors()) {
            model.addAttribute("course", courseDto);
            return "courseForm";
        }

        if (!courseService.saveCourse(courseDto, user)) {
            return "redirect:/";
        }

        return "redirect:/courses";
    }

    @GetMapping("/courses/edit/{courseId}")
    public String updateForm(@PathVariable("courseId") Long courseId,
                             @AuthenticationPrincipal User user,
                             Model model) {
        if (!courseService.canUpdate(user, courseId)) return "redirect:/courses";

        CourseDto foundCourse = courseService.getCourse(courseId).orElse(null);
        if (foundCourse == null) return "redirect:/courses";

        model.addAttribute("course", foundCourse);
        model.addAttribute("method", "put");
        model.addAttribute("actionUrl", "/courses/edit/" + courseId);
        model.addAttribute("buttonText", "Modify");

        return "courseForm";
    }

    @PutMapping("/courses/edit/{courseId}")
    public String editCourse(@PathVariable("courseId") Long courseId,
                             @Valid @ModelAttribute("course") CourseDto courseDto,
                             @AuthenticationPrincipal User user,
                             Model model,
                             BindingResult result) {
        if (!courseService.canUpdate(user, courseId)) return "redirect:/courses";

        verifyDates(courseDto, result);

        if (result.hasErrors()) {
            model.addAttribute("course", courseDto);
            return "courseForm";
        }

        courseService.editCourse(courseId, courseDto, user);

        return "redirect:/courses";
    }

    private void verifyDates(CourseDto courseDto, BindingResult result) {
        if (courseDto.getEndDate().isBefore(courseDto.getStartDate()) || courseDto.getEndDate().isBefore(LocalDate.now())) {
            result.rejectValue("endDate", null, "Zła data.");
        }

        if (courseDto.getStartDate().isBefore(LocalDate.now())) {
            result.rejectValue("startDate", null,"Zła data.");
        }
    }
}
