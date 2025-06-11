package com.example.springboilerplate.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
public class HealthCheckController {

    @GetMapping("/up")
    @RequestMapping(value = "/up", method = RequestMethod.HEAD)
    public ResponseEntity<Map<String, Object>> status() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("timestamp", Instant.now());
        return ResponseEntity.ok(response);
    }
}
