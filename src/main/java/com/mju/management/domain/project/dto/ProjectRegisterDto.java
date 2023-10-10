package com.mju.management.domain.project.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRegisterDto {
    @Schema(description = "프로젝트 이름", defaultValue = "소코아")
    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Schema(description = "프로젝트 시작일", defaultValue = "2023-11-01")
    private LocalDate sDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    @Schema(description = "프로젝트 종료일", defaultValue = "2023-11-07")
    private LocalDate fDate;

    @Schema(description = "프로젝트 내용", defaultValue = "이 프로젝트는 소프트웨어에 관한 프로젝트입니다.")
    private String description;

    @Schema(description = "프로젝트 완료 표시", defaultValue = "false")
    private boolean isChecked;
}