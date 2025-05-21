package com.example.springboilerplate.dto;

import com.example.springboilerplate.validation.PasswordMatches;
import com.example.springboilerplate.validation.ValidEmail;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@PasswordMatches
public class RegisterDto {
    @NotNull
    @NotEmpty
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @NotEmpty
    @ValidEmail
    private String email;

    @NotNull
    @NotEmpty
    @Size(min = 6, max = 100)
    private String password;

    @NotNull
    @NotEmpty
    @JsonProperty("password_confirmation")
    private String matchingPassword;
}
