package com.example.springboilerplate.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String email;
    private String password;
    private String password_confirmation;
}
