package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UsersResponseDto {
    private Long id;
    private String name;
    private String email;
}