package com.example.springboilerplate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SessionsController {

    @GetMapping("/login")
    public String login() {
        return "sessions/new";
    }
}
