package com.example.spring_mvc.controller;

import com.example.spring_mvc.entities.Course;
import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.course.CourseDto;
import com.example.spring_mvc.model.course.ReiterationDto;
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

        if (user.getRole() != User.Role.INSTRUCTOR) {
            return "redirect:/";
        }

        verifyDates(courseDto.getStartDate(), courseDto.getEndDate(), result);

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
    public String editCourseById(@PathVariable("courseId") Long courseId,
                                 @Valid @ModelAttribute("course") CourseDto courseDto,
                                 @AuthenticationPrincipal User user,
                                 Model model,
                                 BindingResult result) {
        if (!courseService.canUpdate(user, courseId)) return "redirect:/courses";

        verifyDates(courseDto.getStartDate(), courseDto.getEndDate(), result);

        if (result.hasErrors()) {
            model.addAttribute("course", courseDto);
            return "courseForm";
        }

        courseService.editCourse(courseId, courseDto);

        return "redirect:/courses";
    }

    @DeleteMapping("courses/delete/{courseId}")
    public String deleteCourseById(@PathVariable("courseId") Long courseId,
                                 @AuthenticationPrincipal User user) {

        courseService.deleteCourse(courseId, user);

        return "redirect:/courses"; // TODO: zrobic jeszcze zeby sie pokazalo czy sie udalo czy nie
    }

    @GetMapping("courses/new-iteration/{courseId}")
    public String reiterationForm(@PathVariable("courseId") Long courseId,
                                  @AuthenticationPrincipal User user,
                                  Model model) {
        if (!courseService.canUpdate(user, courseId)) return "redirect:/courses";

        model.addAttribute("reiteration", ReiterationDto.builder().build());
        model.addAttribute("courseId", courseId);

        return "reiterationForm";
    }

    @PatchMapping("courses/new-iteration/{courseId}")
    public String reiterateCourseById(@PathVariable("courseId") Long courseId,
                                      @Valid @ModelAttribute("reiteration") ReiterationDto reiterationDto,
                                      @AuthenticationPrincipal User user,
                                      Model model,
                                      BindingResult result) {
        if (!courseService.canUpdate(user, courseId)) return "redirect:/courses";

        verifyDates(reiterationDto.getStartDate(), reiterationDto.getEndDate(), result);

        if (result.hasErrors()) {
            model.addAttribute("reiteration", reiterationDto);
            return "reiterationForm";
        }

        courseService.reiterateCourse(courseId, reiterationDto);
        return "redirect:/courses"; // TODO: jak wyżej
    }

    private void verifyDates(LocalDate startDate, LocalDate endDate, BindingResult result) {
        if (endDate.isBefore(startDate) || endDate.isBefore(LocalDate.now())) {
            result.rejectValue("endDate", null, "Zła data.");
        }

        if (startDate.isBefore(LocalDate.now())) {
            result.rejectValue("startDate", null,"Zła data.");
        }
    }
}
