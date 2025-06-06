package com.example.spring_mvc.controller;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.EnrollmentDto;
import com.example.spring_mvc.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/enrollments")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping("/enroll/{courseId}")
    public String enroll(@PathVariable("courseId") Long courseId,
                         @AuthenticationPrincipal User user) {
        enrollmentService.enrollStudent(user, courseId);

        return "redirect:/enrollments";
    }

    @GetMapping
    public String listEnrollments(@AuthenticationPrincipal User user, Model model) {
        List<EnrollmentDto> enrollments = enrollmentService.listEnrollments(user);

        model.addAttribute("enrollments", enrollments);
        model.addAttribute("isAdmin", user.getRole() != User.Role.STUDENT);

        return "enrollments";
    }

    @PatchMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status,
                               @AuthenticationPrincipal User user) {
        enrollmentService.updateStatus(id, status, user);

        return "redirect:/enrollments";
    }
}
