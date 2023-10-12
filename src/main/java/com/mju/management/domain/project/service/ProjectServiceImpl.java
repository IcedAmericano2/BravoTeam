package com.mju.management.domain.project.service;

import com.mju.management.domain.project.dto.response.GetProjectResponseDto;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.project.dto.reqeust.ProjectRegisterRequestDto;
import com.mju.management.global.model.Exception.StartDateAfterEndDateException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public void registerProject(ProjectRegisterRequestDto projectRegisterRequestDto) {
        validateProjectPeriod(projectRegisterRequestDto);
        projectRepository.save(projectRegisterRequestDto.toEntity());
    }

    @Override
    @Transactional
    public List<GetProjectResponseDto> getProjectList() {
        List<GetProjectResponseDto> projectList = projectRepository.findAll()
                .stream().map(GetProjectResponseDto::from)
                .collect(Collectors.toList());
        if (projectList.isEmpty()) throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
        return projectList;
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
    public void updateProject(Long projectIndex, ProjectRegisterRequestDto projectUpdateRequestDto) {
        validateProjectPeriod(projectUpdateRequestDto);
        Project project = projectRepository.findById(projectIndex)
                .orElseThrow(()->new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        project.update(projectUpdateRequestDto);

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

    public void validateProjectPeriod(ProjectRegisterRequestDto projectRegisterRequestDto){
        LocalDate startDate = projectRegisterRequestDto.startDateAsLocalDateType();
        LocalDate endDate = projectRegisterRequestDto.finishDateAsLocalDateType();
        if(startDate.isAfter(endDate))
            throw new StartDateAfterEndDateException(ExceptionList.START_DATE_AFTER_END_DATE_EXCEPTION);
    }
}
