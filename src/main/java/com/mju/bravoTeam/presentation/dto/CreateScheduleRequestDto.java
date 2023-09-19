package com.mju.bravoTeam.presentation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mju.bravoTeam.domain.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateScheduleRequestDto {

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    public Schedule toEntity(){
        return Schedule.builder()
                .title(title)
                .date(date)
                .build();
    }
}
