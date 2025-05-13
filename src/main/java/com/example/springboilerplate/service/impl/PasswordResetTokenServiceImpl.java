package com.example.springboilerplate.service.impl;

import com.example.springboilerplate.model.PasswordResetToken;
import com.example.springboilerplate.model.User;
import com.example.springboilerplate.repository.PasswordResetTokenRepository;
import com.example.springboilerplate.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    @Override
    @Transactional(readOnly = true)
    public boolean validatePasswordResetToken(String token, String email) {
        Optional<PasswordResetToken> tokenOptional = passwordResetTokenRepository.findByToken(token);

        if (tokenOptional.isEmpty()) {
            return false;
        }

        PasswordResetToken resetToken = tokenOptional.get();
        if (resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            return false;
        }

        return resetToken.getUser().getEmail().equalsIgnoreCase(email);
    }
    
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    
    @Override
    @Transactional
    public PasswordResetToken createToken(User user) {
        // Delete any existing token for this user
        passwordResetTokenRepository.findByUser(user).ifPresent(passwordResetTokenRepository::delete);
        
        // Create new token
        String token = UUID.randomUUID().toString();
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setToken(token);
        passwordResetToken.setUser(user);
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(24));
        
        return passwordResetTokenRepository.save(passwordResetToken);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PasswordResetToken> getByToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<PasswordResetToken> getByUser(User user) {
        return passwordResetTokenRepository.findByUser(user);
    }
    
    @Override
    @Transactional
    public void deleteToken(PasswordResetToken token) {
        passwordResetTokenRepository.delete(token);
    }
    
    @Override
    @Transactional
    public void deleteExpiredTokens() {
        passwordResetTokenRepository.deleteAllExpiredSince(LocalDateTime.now());
    }
}
