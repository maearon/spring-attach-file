package com.example.springboilerplate.controller;

import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final UserService userService;

    @GetMapping("/activate")
    public String activate(@RequestParam String token, RedirectAttributes redirectAttributes) {
        boolean activated = userService.activate(token);
        if (activated) {
            redirectAttributes.addFlashAttribute("success", "Account activated successfully! You can now log in.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid activation link.");
        }
        return "redirect:/login";
    }
}
