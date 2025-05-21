package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserShowDto {
    private String id;
    private String name;
    private String email;
    private String gravatar;
    private long following;
    private long followers;
    private boolean current_user_following_user;
}
