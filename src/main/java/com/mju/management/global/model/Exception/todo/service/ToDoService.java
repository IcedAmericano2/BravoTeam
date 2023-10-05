package com.mju.management.global.model.Exception.todo.service;

import com.mju.management.global.model.Exception.todo.infrastructure.ToDoEntity;
import com.mju.management.global.model.Exception.todo.dto.ToDoRegisterDto;

import java.util.List;

public interface ToDoService {
    public void registerToDo(ToDoRegisterDto toDoRegisterDto);

    public List<ToDoEntity> getToDo();

    public void deleteToDo(Long todoIndex);

    public ToDoEntity showToDoOne(Long todoIndex);

    public void updateToDo(Long todoIndex, ToDoRegisterDto toDoRegisterDto);

    public void finishToDo(Long todoIndex);
}
