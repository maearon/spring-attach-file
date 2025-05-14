package com.example.springboilerplate.controller;

import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/password_resets")
@RequiredArgsConstructor
public class PasswordResetsController {

    private final UserService userService;

    @GetMapping("/new")
    public String newPasswordReset() {
        return "password_resets/new";
    }

    @PostMapping
    public String create(@RequestParam String email, RedirectAttributes redirectAttributes) {
        userService.findByEmail(email).ifPresent(userService::createPasswordReset);
        redirectAttributes.addFlashAttribute("info", "Email sent with password reset instructions");
        return "redirect:/login";
    }

    @GetMapping("/edit")
    public String edit(@RequestParam String token, Model model) {
        model.addAttribute("token", token);
        return "password_resets/edit";
    }

    @PostMapping("/update")
    public String update(@RequestParam String token, @RequestParam String password,
                         RedirectAttributes redirectAttributes) {
        boolean updated = userService.resetPassword(token, password);
        if (updated) {
            redirectAttributes.addFlashAttribute("success", "Password has been reset.");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid or expired password reset link.");
            return "redirect:/password_resets/new";
        }
    }
}
