package com.example.springboilerplate.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SessionResponseDto {
    // private String accessToken;
    // @Builder.Default
    // private String tokenType = "Bearer";
    // private Long id;
    // private String name;
    // private String email;
    // private boolean admin;
    private UserDto user;
    private TokenGroupDto tokens;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDto {
        private String id;
        private String email;
        private String name;
        private boolean admin;
        private String gravatar;
        // private String passwordHash;
        // private String token;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenGroupDto {
        private TokenDto access;
        private TokenDto refresh;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenDto {
        private String token;
        private Instant expires;
    }
}
