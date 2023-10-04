package com.mju.management.domain.todo.service;

import com.mju.management.domain.todo.infrastructure.ToDoEntity;
import com.mju.management.domain.todo.dto.ToDoRegisterDto;

import java.util.List;

public interface ToDoService {
    public void registerCheckList(ToDoRegisterDto toDoRegisterDto);

    public List<ToDoEntity> getCheckList();

    public void deleteCheckList(Long checkListIndex);

    public ToDoEntity showCheckListOne(Long checkListIndex);

    public void updateCheckList(Long checkListIndex, ToDoRegisterDto toDoRegisterDto);

    public void finishCheckList(Long checkListIndex);
}
