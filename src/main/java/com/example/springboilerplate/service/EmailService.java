package com.example.springboilerplate.service;

import com.example.springboilerplate.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendActivationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Account Activation");
        message.setText("Welcome to the Sample App! Click on the link below to activate your account:\n\n" +
                "http://localhost:8080/account/activate?token=" + user.getActivationDigest());
        mailSender.send(message);
    }

    public void sendPasswordResetEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset");
        message.setText("To reset your password click the link below:\n\n" +
                "http://localhost:8080/password_resets/edit?token=" + user.getResetDigest() + "\n\n" +
                "This link will expire in two hours.\n\n" +
                "If you did not request your password to be reset, please ignore this email and your password will stay as it is.");
        mailSender.send(message);
    }
}
