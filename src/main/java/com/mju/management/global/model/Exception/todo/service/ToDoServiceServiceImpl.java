package com.mju.management.global.model.Exception.todo.service;

import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.global.model.Exception.todo.infrastructure.ToDoEntity;
import com.mju.management.global.model.Exception.todo.dto.ToDoRegisterDto;
import com.mju.management.global.model.Exception.todo.infrastructure.ToDoJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoServiceServiceImpl implements ToDoService {
    private final ToDoJpaRepository toDoJpaRepository;

    @Override
    @Transactional
    public void registerToDo(ToDoRegisterDto toDoRegisterDto) {
        ToDoEntity toDoEntity = ToDoEntity.builder()
                .todoContent(toDoRegisterDto.getTodoContent())
                .build();
        toDoJpaRepository.save(toDoEntity);
    }

    @Override
    @Transactional
    public List<ToDoEntity> getToDo() {
        Sort sort = Sort.by(Sort.Order.asc("isChecked"));
        List<ToDoEntity> toDoEntity =  toDoJpaRepository.findAll(sort);
        if (!toDoEntity.isEmpty()) {
            return toDoEntity;
        }else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void deleteToDo(Long todoIndex) {
        Optional<ToDoEntity> optionalToDo = toDoJpaRepository.findById(todoIndex);
        if (optionalToDo.isPresent()) { // 해당 index의 CheckList가 존재하는 경우
            toDoJpaRepository.deleteById(todoIndex);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public ToDoEntity showToDoOne(Long todoIndex) {
        Optional<ToDoEntity> optionalToDo = toDoJpaRepository.findById(todoIndex);
        if (optionalToDo.isPresent()) {
            ToDoEntity toDoEntity = optionalToDo.get();
            return toDoEntity;
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void updateToDo(Long todoIndex, ToDoRegisterDto toDoRegisterDto) {
        Optional<ToDoEntity> optionalToDo = toDoJpaRepository.findById(todoIndex);
        if (optionalToDo.isPresent()) {
            ToDoEntity toDoEntity = optionalToDo.get();
            toDoEntity.update(toDoRegisterDto.getTodoContent());
            toDoJpaRepository.save(toDoEntity);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void finishToDo(Long todoIndex) {
        Optional<ToDoEntity> optionalToDo = toDoJpaRepository.findById(todoIndex);
        if (optionalToDo.isPresent()) {
            ToDoEntity toDoEntity = optionalToDo.get();
            toDoEntity.finish();
            toDoJpaRepository.save(toDoEntity);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

}
