package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class MicropostResponseDto {
    private Long id;
    private String content;
    // private String gravatar_id;
    private LocalDateTime createdAt;
    private UserSummaryDto user;
    private List<String> imageUrls;
}
