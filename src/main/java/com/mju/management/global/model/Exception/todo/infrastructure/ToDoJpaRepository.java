package com.mju.management.global.model.Exception.todo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoJpaRepository extends JpaRepository<ToDoEntity, Long> {
}
