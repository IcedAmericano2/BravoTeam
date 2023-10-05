package com.mju.management.global.model.Exception.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ToDoRegisterDto {

    @Schema(description = "할일 내용", defaultValue = "오늘 할일 완료")
    private String todoContent;

}

