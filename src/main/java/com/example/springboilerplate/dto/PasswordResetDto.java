package com.example.springboilerplate.dto;

import com.example.springboilerplate.validation.PasswordMatches;
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
public class PasswordResetDto {
    @NotNull
    @NotEmpty
    @Size(min = 6, max = 100)
    private String password;

    @NotNull
    @NotEmpty
    private String matchingPassword;

    private String token;


    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
