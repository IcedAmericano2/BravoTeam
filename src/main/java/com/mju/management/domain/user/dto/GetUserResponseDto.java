package com.mju.management.domain.user.dto;

import lombok.Getter;

@Getter
public class GetUserResponseDto {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private boolean isApproved;
}
