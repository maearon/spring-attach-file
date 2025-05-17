package com.example.springboilerplate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class FollowingResponseDto {
    private List<UserSummaryDto> users;
    private int totalElements;
    private List<UserSummaryDto> xusers;
    private UserDetailDto user;
}