package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RelationshipDto {
    private Long id;
    private Long followerId;
    private Long followedId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
