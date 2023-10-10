package com.mju.management.domain.todo.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mju.management.domain.project.infrastructure.Project;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "todo")
public class ToDoEntity {
    @Builder
    public ToDoEntity(String todoContent){
        this.todoContent = todoContent;
        this.isChecked = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "todo_index")
    private Long todoIndex;

    @Column(name = "todo_content")
    private String todoContent;

    @Column(name = "isChecked")
    private boolean isChecked;

    @ManyToOne
    @JoinColumn(name = "project_index")
    @JsonIgnore
    private Project project;

    public void setProject(Project project){
        this.project = project;
    }

    public void update(String todoContent) {
        this.todoContent = todoContent;
        this.isChecked = false;
    }

    public void finish() {
        this.isChecked = true;
    }
}
