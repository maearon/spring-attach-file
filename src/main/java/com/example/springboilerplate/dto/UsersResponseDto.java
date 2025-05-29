package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import org.springframework.util.DigestUtils;

@Data
@AllArgsConstructor
public class UsersResponseDto {
    private String id;
    private String name;
    private String username;
    private String email;
    private String gravatar;

    public UsersResponseDto(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.gravatar = buildGravatar(email);
    }

    private String buildGravatar(String email) {
        String validEmail = (email != null) ? email.trim().toLowerCase() : "test@example.com";
        String emailHash = DigestUtils.md5DigestAsHex(validEmail.trim().toLowerCase().getBytes());
        return "https://secure.gravatar.com/avatar/" + emailHash + "?s=50";
    }
}