package com.mju.management.project.service;

import com.mju.management.project.infrastructure.Project;
import com.mju.management.project.dto.ProjectRegisterDto;

import java.util.List;

public interface ProjectService {

    public void registerProject(ProjectRegisterDto projectRegisterDto);

    public List<Project> getProject();

    public void deleteProject(Long projectIndex);

    public void updateProject(Long projectIndex, ProjectRegisterDto projectRegisterDto);

    public void finishProject(Long projectIndex);
}
