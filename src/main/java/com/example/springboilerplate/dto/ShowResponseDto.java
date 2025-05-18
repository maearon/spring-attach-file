package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

@Data
@AllArgsConstructor
public class ShowResponseDto {
    @JsonProperty("id_relationships")
    private Long idRelationships;
    private List<MicropostResponseDto> microposts;
    @JsonProperty("total_count")
    private long totalElements;
    private UserShowDto user;
}
