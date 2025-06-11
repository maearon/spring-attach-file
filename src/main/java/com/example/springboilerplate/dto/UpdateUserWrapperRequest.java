package com.example.springboilerplate.dto;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserWrapperRequest {
    @Valid
    private UpdateUserRequest user;
}
