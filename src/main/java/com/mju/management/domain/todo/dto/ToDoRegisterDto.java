package com.mju.management.domain.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ToDoRegisterDto {

    @Schema(description = "할일 내용", defaultValue = "할일 내용")
    private String todoContent;

    @Schema(description = "긴급 표시", defaultValue = "긴급 표시")
    private boolean todoEmergency;

}

