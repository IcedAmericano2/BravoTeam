package com.mju.management.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mju.management.domain.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateScheduleRequestDto {

    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public Schedule toEntity(){
        return Schedule.builder()
                .content(content)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
