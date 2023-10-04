package com.mju.management.todo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoJpaRepository extends JpaRepository<ToDoEntity, Long> {
}
