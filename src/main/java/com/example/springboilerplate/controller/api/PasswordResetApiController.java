package com.example.springboilerplate.controller.api;

import com.example.springboilerplate.dto.ApiResponse;
import com.example.springboilerplate.dto.PasswordResetDto;
import com.example.springboilerplate.service.PasswordResetTokenService;
import com.example.springboilerplate.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.Map;

@RestController
@RequestMapping("/api/password_resets")
@RequiredArgsConstructor
public class PasswordResetApiController {

    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;

    @PostMapping
    public ResponseEntity<?> createPasswordReset(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isEmpty()) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email is required"));
        }

        boolean result = userService.requestPasswordReset(email);
        if (!result) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Email not found"));
        }

        return ResponseEntity.ok(new ApiResponse(true, "Password reset email sent"));
    }

    @PutMapping("/{token}")
    public ResponseEntity<?> updatePassword(@PathVariable String token,
                                           @Valid @RequestBody PasswordResetDto passwordResetDto) {
        boolean result = passwordResetTokenService.validatePasswordResetToken(token, passwordResetDto.getEmail());
        if (!result) {
            return ResponseEntity.badRequest().body(new ApiResponse(false, "Invalid or expired password reset token"));
        }

        userService.resetPassword(passwordResetDto.getEmail(), passwordResetDto.getPassword());
        return ResponseEntity.ok(new ApiResponse(true, "Password has been reset successfully"));
    }
}
