package com.mju.management.domain.project.infrastructure;

import com.mju.management.domain.project.dto.reqeust.ProjectRegisterRequestDto;
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
    public Project(String name, LocalDate startDate, LocalDate finishDate, String description){
        this.name = name;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.description = description;
        this.isChecked = false;
    }

    @Id
    @Column(name = "project_index")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectIndex;

    @Column(name = "project_name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "finish_date")
    private LocalDate finishDate;

    @Column(name = "isChecked")
    private boolean isChecked;

    // Post(기획, 제작, 편집 게시글)와 연관 관계
    @OneToMany(mappedBy = "project", cascade = ALL, orphanRemoval = true)
    private List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = ALL, orphanRemoval = true)
    private List<Schedule> scheduleList = new ArrayList<>();

    public void createPost(Post post){
        this.postList.add(post);
        post.setProject(this);
    }

    public void update(ProjectRegisterRequestDto projectUpdateRequestDto){
        this.name = projectUpdateRequestDto.getName();
        this.description = projectUpdateRequestDto.getDescription();
        this.startDate = projectUpdateRequestDto.startDateAsLocalDateType();
        this.finishDate = projectUpdateRequestDto.finishDateAsLocalDateType();
    }

    public void finish() {
        this.isChecked = true;
    }
}
