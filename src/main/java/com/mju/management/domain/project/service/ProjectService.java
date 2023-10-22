package com.mju.management.domain.project.service;

import com.mju.management.domain.project.dto.reqeust.ProjectRegisterRequestDto;
import com.mju.management.domain.project.dto.response.GetProjectListResponseDto;
import com.mju.management.domain.project.dto.response.GetProjectResponseDto;

import java.util.List;

public interface ProjectService {

    void registerProject(ProjectRegisterRequestDto projectRegisterRequestDto);
    List<GetProjectListResponseDto> getProjectList();
    List<GetProjectListResponseDto> getMyProjectList();
    GetProjectResponseDto getProject(Long projectIndex);
    void deleteProject(Long projectIndex);
    void updateProject(Long projectIndex, ProjectRegisterRequestDto projectRegisterRequestDto);
    void finishProject(Long projectIndex);

}
