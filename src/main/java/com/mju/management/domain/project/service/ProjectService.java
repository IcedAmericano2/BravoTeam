package com.mju.management.domain.project.service;

import com.mju.management.domain.project.dto.ProjectRegisterDto;
import com.mju.management.domain.project.infrastructure.Project;

import java.util.List;

public interface ProjectService {

    public void registerProject(/*String userId,*/ ProjectRegisterDto projectRegisterDto);

    public List<Project> getProject();

    public void deleteProject(Long projectIndex);

    public void updateProject(Long projectIndex, ProjectRegisterDto projectRegisterDto);

    public void finishProject(Long projectIndex);
}
