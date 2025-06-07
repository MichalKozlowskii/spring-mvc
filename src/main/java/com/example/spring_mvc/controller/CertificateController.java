package com.example.spring_mvc.controller;

import com.example.spring_mvc.entities.User;
import com.example.spring_mvc.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/issue")
    public String listFinishedEnrollmentsNotCertified(@AuthenticationPrincipal User user, Model model) {
        if (user.getRole() == User.Role.STUDENT) return "redirect:/certificates";

        model.addAttribute("enrollments", certificateService.listFinishedEnrollmentsNotCertified(user));

        return "issue_certificates";
    }

    @PostMapping("/issue/{enrollmentId}")
    public String issueCertificate(@PathVariable Long enrollmentId,
                                   @AuthenticationPrincipal User user) {

        certificateService.issueCertificate(enrollmentId, user);
        return "redirect:/certificates/issue";
    }

    @DeleteMapping("/revoke/{certificateId}")
    public String revokeCertificate(@PathVariable Long certificateId,
                                    @AuthenticationPrincipal User user) {

        certificateService.revokeCertificate(certificateId, user);
        return "redirect:/certificates";
    }
}
