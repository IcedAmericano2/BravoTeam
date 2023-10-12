package com.mju.management.domain.project.service;

import com.mju.management.domain.project.dto.reqeust.ProjectRegisterRequestDto;
import com.mju.management.domain.project.dto.response.GetProjectResponseDto;

import java.util.List;

public interface ProjectService {

    public void registerProject(ProjectRegisterRequestDto projectRegisterRequestDto);

    public List<GetProjectResponseDto> getProjectList();

    public void deleteProject(Long projectIndex);

    public void updateProject(Long projectIndex, ProjectRegisterRequestDto projectRegisterRequestDto);

    public void finishProject(Long projectIndex);
}
