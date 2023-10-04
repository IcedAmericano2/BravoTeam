package com.mju.management.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectRegisterDto {
    private String name;
    private String description;
    private boolean isChecked;
}