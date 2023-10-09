package com.mju.management.domain.project.infrastructure;

import com.mju.management.domain.schedule.infrastructure.Schedule;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mju.management.domain.post.domain.Post;

import static jakarta.persistence.CascadeType.ALL;

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

    // Post(기획, 제작, 편집 게시글)와 연관 관계
    @OneToMany
    @JoinColumn(name = "project")
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = ALL, orphanRemoval = true)
    private List<Schedule> scheduleList = new ArrayList<>();

    public void createPost(Post post){
        this.postList.add(post);
        post.setProject(this);
    }

    public void update(String name, LocalDate sDate, LocalDate fDate, String description){
        this.name = name;
        this.sDate = sDate;
        this.fDate = fDate;
        this.description = description;
        this.isChecked = false;
    }

    public void finish() {
        this.isChecked = true;
    }
}
