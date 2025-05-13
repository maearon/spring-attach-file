package com.example.springboilerplate.controller;

import com.example.springboilerplate.model.Micropost;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.service.MicropostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StaticPagesController {

    private final MicropostService micropostService;

    @GetMapping("/")
    public String home(@AuthenticationPrincipal User currentUser, Model model) {
        if (currentUser != null) {
            model.addAttribute("micropost", new Micropost());
            Page<Micropost> feed = micropostService.getFeed(currentUser.getId(), PageRequest.of(0, 10));
            model.addAttribute("feed", feed);
            return "home/home_with_feed";
        }
        return "home/home";
    }

    @GetMapping("/help")
    public String help() {
        return "static_pages/help";
    }

    @GetMapping("/about")
    public String about() {
        return "static_pages/about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "static_pages/contact";
    }
}
