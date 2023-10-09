package com.mju.management.domain.todo.infrastructure;

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
    public ToDoEntity(String todoContent, boolean todoEmergency){
        this.todoContent = todoContent;
        this.todoEmergency = todoEmergency;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_index")
    private Long todoIndex;

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
