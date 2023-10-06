package com.mju.management.domain.project.service;

import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.project.dto.ProjectRegisterDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public void registerProject(ProjectRegisterDto projectRegisterDto) {
        Project project = Project.builder()
                .name(projectRegisterDto.getName())
                .description(projectRegisterDto.getDescription())
                .build();
        projectRepository.save(project);
    }

    @Override
    @Transactional
    public List<Project> getProject() {
        List<Project> project = projectRepository.findAll();
        if (!project.isEmpty()) {
            return project;
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
        }
    }

    @Override
    @Transactional
    public void deleteProject(Long projectIndex) {
        Optional<Project> optionalProject = projectRepository.findById(projectIndex);
        if (optionalProject.isPresent()) {
            projectRepository.deleteById(projectIndex);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
        }
    }

    @Override
    @Transactional
    public void updateProject(Long projectIndex, ProjectRegisterDto projectRegisterDto) {
        Optional<Project> optionalProject = projectRepository.findById(projectIndex);
        if (optionalProject.isPresent()){
            Project project = optionalProject.get();
            project.update(projectRegisterDto.getName(), projectRegisterDto.getSDate(), projectRegisterDto.getFDate(), projectRegisterDto.getDescription());
            projectRepository.save(project);
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
        }
    }

    @Override
    @Transactional
    public void finishProject(Long projectIndex) {
        Optional<Project> optionalProject = projectRepository.findById(projectIndex);
        if (optionalProject.isPresent()) {
            Project project = optionalProject.get();
            project.finish();
        } else {
            throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
        }
    }
}
