package com.mju.management.domain.todo.infrastructure;

import com.mju.management.domain.project.infrastructure.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToDoJpaRepository extends JpaRepository<ToDoEntity, Long> {
    List<ToDoEntity> findByProjectOrderByIsCheckedAsc(Project project);
}
