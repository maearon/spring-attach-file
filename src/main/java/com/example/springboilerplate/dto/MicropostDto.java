package com.example.springboilerplate.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MicropostDto {
    private Long id;

    @NotNull
    @NotEmpty
    @Size(min = 1, max = 140)
    private String content;

    private MultipartFile image;
    private String imagePath;
    private UserDto user;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
