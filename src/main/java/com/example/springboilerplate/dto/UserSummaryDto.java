package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserSummaryDto {
    private String id;
    private String name;
    private String email;
    private String gravatar;
}
