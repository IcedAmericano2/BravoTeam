package com.mju.management.domain.todo.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoJpaRepository extends JpaRepository<ToDoEntity, Long> {
}
