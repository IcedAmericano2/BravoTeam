package com.mju.management.schedule.infrastructure;

import com.mju.management.schedule.dto.CreateScheduleRequestDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {
    @Builder
    public Schedule(String content, LocalDate startDate, LocalDate endDate){
        this.content = content;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_index")
    private Long scheduleIndex;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public void update(CreateScheduleRequestDto updateScheduleRequestDto) {
        this.content = updateScheduleRequestDto.getContent();
        this.startDate = updateScheduleRequestDto.getStartDate();
        this.endDate = updateScheduleRequestDto.getEndDate();
    }
}
