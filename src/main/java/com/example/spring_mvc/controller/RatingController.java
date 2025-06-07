package com.example.spring_mvc.controller;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping
    public String listRatings(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("ratings", ratingService.listRatings(user));
        model.addAttribute("isAdmin", user.getRole() != User.Role.STUDENT);

        return "ratings";
    }
}
