package com.mju.management.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "project")
public class Project {

    @Builder
    public Project(String name, LocalDate sDate, LocalDate fDate, String description){
        this.name = name;
        this.sDate = sDate;
        this.fDate = fDate;
        this.description = description;
        this.isChecked = false;
    }

    @Id
    @Column(name = "project_index")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long projectIndex;

    @Column(name = "project_name")
    private String name;

    @Column(name = "start_date")
    private LocalDate sDate;

    @Column(name = "finish_date")
    private LocalDate fDate;

    @Column(name = "description")
    private String description;

    @Column(name = "isChecked")
    private boolean isChecked;

    @PrePersist
    public void prePersist(){
        this.sDate = LocalDate.now();
    }

    public void update(String name, String description){
        this.name = name;
//        this.fDate = fDate;
        this.description = description;
        this.isChecked = false;
    }

    public void finish() {
        this.isChecked = true;
    }
}
