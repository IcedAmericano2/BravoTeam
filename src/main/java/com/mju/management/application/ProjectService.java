package com.mju.management.application;

import com.mju.management.domain.model.Project;
import com.mju.management.presentation.dto.ProjectRegisterDto;

import java.util.List;

public interface ProjectService {

    public void registerProject(ProjectRegisterDto projectRegisterDto);

    public List<Project> getProject();

    public void deleteProject(Long projectIndex);

    public void updateProject(Long projectIndex, ProjectRegisterDto projectRegisterDto);

    public void finishProject(Long projectIndex);
}
