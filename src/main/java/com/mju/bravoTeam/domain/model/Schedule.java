package com.mju.bravoTeam.domain.model;

import com.zaxxer.hikari.util.UtilityElf;
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
    public Schedule(String title, LocalDate date){
        this.title = title;
        this.date = date;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_index")
    private Long scheduleIndex;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public void update(String title, LocalDate date) {
        this.title = title;
        this.date = date;
    }
}
