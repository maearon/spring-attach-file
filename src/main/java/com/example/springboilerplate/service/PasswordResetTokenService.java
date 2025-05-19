package com.example.springboilerplate.service;

import com.example.springboilerplate.model.PasswordResetToken;
import com.example.springboilerplate.model.User;

// import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
// @RequiredArgsConstructor
public interface PasswordResetTokenService {
    PasswordResetToken createToken(User user);
    
    Optional<PasswordResetToken> getByToken(String token);
    
    Optional<PasswordResetToken> getByUser(User user);
    
    void deleteToken(PasswordResetToken token);
    
    void deleteExpiredTokens();

    boolean validatePasswordResetToken(String token, String email);
}


