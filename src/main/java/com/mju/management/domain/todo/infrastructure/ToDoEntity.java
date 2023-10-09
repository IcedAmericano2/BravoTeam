package com.mju.management.domain.todo.infrastructure;

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
    public ToDoEntity(String todoContent, boolean todoEmergency,Project project){
        this.todoContent = todoContent;
        this.todoEmergency = todoEmergency;
        this.project = project;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_index")
    private Long todoIndex;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "todo_content")
    private String todoContent;

    @Column(name = "is_checked")
    private boolean isChecked;

    @Column(name = "todo_emergency")
    private boolean todoEmergency;

    public void update(String todoContent) {
        this.todoContent = todoContent;
        this.isChecked = false;
    }

    public void finish(boolean isChecked) {
        if(!isChecked){
            this.isChecked = true;
        }else {
            this.isChecked = false;
        }

    }
}
