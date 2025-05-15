package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class MicropostCreateResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private String flash;
    private UserSummaryDto user;
}
