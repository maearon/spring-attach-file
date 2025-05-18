package com.example.springboilerplate.dto;

import com.example.springboilerplate.validation.PasswordMatches;
import com.example.springboilerplate.validation.ValidEmail;
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
public class UserDto {
    private String id;

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
    private String matchingPassword;

    private boolean admin;
    private boolean activated;
    private String activationToken;
    private String gravatarUrl;
    private int followersCount;
    private int followingCount;
    private int micropostsCount;
    private boolean following;
}
