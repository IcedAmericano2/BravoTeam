package com.mju.management.domain.todo.service;

import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.domain.todo.infrastructure.ToDoEntity;
import com.mju.management.domain.todo.dto.ToDoRegisterDto;
import com.mju.management.domain.todo.infrastructure.ToDoJpaRepository;
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
    public void registerCheckList(ToDoRegisterDto toDoRegisterDto) {
        ToDoEntity toDoEntity = ToDoEntity.builder()
                .checkListContent(toDoRegisterDto.getCheckListContent())
                .build();
        toDoJpaRepository.save(toDoEntity);
    }

    @Override
    @Transactional
    public List<ToDoEntity> getCheckList() {
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
    public void deleteCheckList(Long checkListIndex) {
        Optional<ToDoEntity> optionalCheckList = toDoJpaRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) { // 해당 index의 CheckList가 존재하는 경우
            toDoJpaRepository.deleteById(checkListIndex);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public ToDoEntity showCheckListOne(Long checkListIndex) {
        Optional<ToDoEntity> optionalCheckList = toDoJpaRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) {
            ToDoEntity toDoEntity = optionalCheckList.get();
            return toDoEntity;
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void updateCheckList(Long checkListIndex, ToDoRegisterDto toDoRegisterDto) {
        Optional<ToDoEntity> optionalCheckList = toDoJpaRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) {
            ToDoEntity toDoEntity = optionalCheckList.get();
            toDoEntity.update(toDoRegisterDto.getCheckListContent());
            toDoJpaRepository.save(toDoEntity);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

    @Override
    @Transactional
    public void finishCheckList(Long checkListIndex) {
        Optional<ToDoEntity> optionalCheckList = toDoJpaRepository.findById(checkListIndex);
        if (optionalCheckList.isPresent()) {
            ToDoEntity toDoEntity = optionalCheckList.get();
            toDoEntity.finish();
            toDoJpaRepository.save(toDoEntity);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

}
