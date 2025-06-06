package com.example.spring_mvc.controller;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/certificates")
@RequiredArgsConstructor
public class CertificateController {
    private final CertificateService certificateService;

        @GetMapping
        public String listCertificates(@AuthenticationPrincipal User user, Model model) {

            model.addAttribute("certificates", certificateService.listCertificates(user));
            model.addAttribute("isAdmin", user.getRole() != User.Role.STUDENT);

            return "certificates";
        }
}
