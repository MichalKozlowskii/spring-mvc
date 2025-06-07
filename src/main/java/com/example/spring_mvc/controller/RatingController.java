package com.example.spring_mvc.controller;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.model.RatingDto;
import com.example.spring_mvc.model.course.CourseDto;
import com.example.spring_mvc.service.CourseService;
import com.example.spring_mvc.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    private final CourseService courseService;

    @GetMapping
    public String listRatings(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("ratings", ratingService.listRatings(user));
        model.addAttribute("isAdmin", user.getRole() != User.Role.STUDENT);

        return "ratings";
    }

    @GetMapping("/add/{courseId}")
    public String addRatingForm(@PathVariable Long courseId, Model model) {
        CourseDto course = courseService.getCourse(courseId).orElse(null);
        if (course == null) return "redirect:/enrollments";

        model.addAttribute("rating", RatingDto.builder().course(course).build());
        model.addAttribute("method", "post");
        model.addAttribute("actionUrl", "/ratings/add/" + courseId);
        model.addAttribute("buttonText", "Add Rating");
        return "ratingForm";
    }

    @PostMapping("/add/{courseId}")
    public String addRating(@PathVariable Long courseId,
                            @AuthenticationPrincipal User user,
                            @Valid @ModelAttribute("rating") RatingDto ratingDto,
                            BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/ratings/add/" + courseId;
        }

        if (!ratingService.addRating(courseId, ratingDto, user)) return "redirect:/enrollments";

        return "redirect:/ratings";
    }

    @GetMapping("/edit/{ratingId}")
    public String editRatingForm(@PathVariable Long ratingId, Model model,
                                 @AuthenticationPrincipal User user) {
        RatingDto rating = ratingService.getRatingById(ratingId);
        if (rating == null || !rating.getUser().getId().equals(user.getId())) return "redirect:/ratings";

        model.addAttribute("rating", rating);
        model.addAttribute("method", "patch");
        model.addAttribute("actionUrl", "/ratings/edit/" + ratingId);
        model.addAttribute("buttonText", "Update Rating");

        return "ratingForm";
    }
}
