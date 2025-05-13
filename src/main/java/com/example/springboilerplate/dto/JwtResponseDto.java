package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponseDto {
    private String accessToken;
    private String tokenType = "Bearer";
    private Long id;
    private String name;
    private String email;
    private boolean admin;

    public JwtResponseDto(String accessToken, Long id, String name, String email, boolean admin) {
        this.accessToken = accessToken;
        this.id = id;
        this.name = name;
        this.email = email;
        this.admin = admin;
    }
}
