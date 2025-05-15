package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MicropostResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private UserSummaryDto user;
}
