package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailDto {
    private String id;
    private String name;
    private String email;
    private int following;
    private int followers;
    private int micropost;
    // private String gravatar;
}
