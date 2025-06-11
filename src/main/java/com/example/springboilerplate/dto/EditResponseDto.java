package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditResponseDto {
    private UserSummaryDto user;
    private String gravatar;
}