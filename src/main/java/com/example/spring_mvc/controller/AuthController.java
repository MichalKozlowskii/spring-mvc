package com.example.spring_mvc.controller;

import com.example.spring_mvc.model.UserAuthDto;
import com.example.spring_mvc.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", UserAuthDto.builder().build());
        return "register";
    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user") UserAuthDto userAuthDto,
                           BindingResult result,
                           Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userAuthDto);
            return "register";
        }

        if (!userService.registerUser(userAuthDto)) {
            result.rejectValue("userName", null,     "username already taken.");
            model.addAttribute("user", userAuthDto);
            return "register";
        }

        return "redirect:/login?registerSuccess";
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }
}
