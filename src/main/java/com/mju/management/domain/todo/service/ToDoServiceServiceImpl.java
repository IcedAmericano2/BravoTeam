package com.mju.management.domain.todo.service;

import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import com.mju.management.domain.todo.dto.ToDoRequestDto;
import com.mju.management.domain.todo.infrastructure.ToDoEntity;
import com.mju.management.domain.todo.infrastructure.ToDoJpaRepository;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.NonExistentException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoServiceServiceImpl implements ToDoService {
    private final ToDoJpaRepository toDoJpaRepository;

    private final ProjectRepository projectRepository;
    @Override
    @Transactional
    public void registerToDo(Long projectId, ToDoRequestDto toDoRequestDto) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();

            ToDoEntity toDoEntity = ToDoEntity.builder()
                    .todoContent(toDoRequestDto.getTodoContent())
                    .todoEmergency(toDoRequestDto.isTodoEmergency())
                    .project(project)
                    .build();
            toDoJpaRepository.save(toDoEntity);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
        }

    }

    @Override
    @Transactional
    public List<ToDoEntity> getToDo(Long projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            List<ToDoEntity> toDoEntity = toDoJpaRepository.findByProjectAndOrderByConditions(project);

            if (!toDoEntity.isEmpty()) {
                return toDoEntity;
            }else {
                throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
            }
        }else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
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
    public void updateToDo(Long todoIndex, ToDoRequestDto toDoRequestDto) {
        Optional<ToDoEntity> optionalToDo = toDoJpaRepository.findById(todoIndex);
        if (optionalToDo.isPresent()) {
            ToDoEntity toDoEntity = optionalToDo.get();
            toDoEntity.update(toDoRequestDto.getTodoContent());
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
            toDoEntity.finish(toDoEntity.isChecked());
            toDoJpaRepository.save(toDoEntity);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_CHECKLIST);
        }
    }

}
