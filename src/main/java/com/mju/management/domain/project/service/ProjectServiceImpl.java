package com.mju.management.domain.project.service;

import com.mju.management.domain.project.dto.response.GetProjectResponseDto;
import com.mju.management.domain.project.infrastructure.*;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.domain.project.dto.reqeust.ProjectRegisterRequestDto;
import com.mju.management.global.model.Exception.StartDateAfterEndDateException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{
    private final ProjectRepository projectRepository;
    private final ProjectUserRepository projectUserRepository;

    @Override
    @Transactional
    public void registerProject(/*String requestUserId,*/ ProjectRegisterRequestDto projectRegisterRequestDto) {
        validateProjectPeriod(projectRegisterRequestDto);
        Project project = projectRepository.save(projectRegisterRequestDto.toEntity());
        Set<Long> memberIdList = projectRegisterRequestDto.getMemberIdList()/*.remove(requestUserId)*/;
        //ToDo : 요청자의 아이디로 Leader 역할의 ProjectUser 엔터티 생성하고 projectUserList에 넣기
        for(Long memberId : memberIdList)
            project.getProjectUserList().add(
                    ProjectUser.builder()
                    .project(project)
                    .userId(memberId)
                    .role(Role.MEMBER)
                    .build()
            );
    }

    @Override
    @Transactional
    public List<GetProjectResponseDto> getProjectList(/*String requestUserId*/) {
        List<GetProjectResponseDto> projectList = projectRepository.findAll()
                .stream().map(GetProjectResponseDto::from)
                .collect(Collectors.toList());
        if (projectList.isEmpty()) throw new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT);
        return projectList;
    }

    @Override
    @Transactional
    public void deleteProject(/*String requestUserId,*/ Long projectIndex) {
        Project project = projectRepository.findById(projectIndex)
                .orElseThrow(()->new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        // TODO : 요청자가 해당 프로젝트의 팀장인지 확인하는 과정 필요
        projectRepository.delete(project);

    }

    @Override
    @Transactional
    public void updateProject(/*String requestUserId,*/ Long projectIndex, ProjectRegisterRequestDto projectUpdateRequestDto) {
        validateProjectPeriod(projectUpdateRequestDto);
        Project project = projectRepository.findByIdWithProjectUserList(projectIndex)
                .orElseThrow(() -> new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        // TODO : 요청자가 해당 프로젝트의 팀장인지 확인하는 과정 필요
        project.update(projectUpdateRequestDto);

        Set<Long> requestMemberIdList = projectUpdateRequestDto.getMemberIdList()/*.remove(requestUserId)*/;
        // 요청 dto에 있지만 db에는 없는 팀원을 추가
        addProjectUser(project, requestMemberIdList);
        // 요청 dto에 없지만 db에는 있는 팀원을 삭제
        deleteProjectUser(project, requestMemberIdList);
    }

    @Override
    @Transactional
    public void finishProject(/*String requestUserId,*/ Long projectIndex) {
        Project project = projectRepository.findById(projectIndex)
                .orElseThrow(()->new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        // TODO : 요청자가 해당 프로젝트의 팀장인지 확인하는 과정 필요
        project.finish();

    }

    public void validateProjectPeriod(ProjectRegisterRequestDto projectRegisterRequestDto){
        LocalDate startDate = projectRegisterRequestDto.startDateAsLocalDateType();
        LocalDate endDate = projectRegisterRequestDto.finishDateAsLocalDateType();
        if(startDate.isAfter(endDate))
            throw new StartDateAfterEndDateException(ExceptionList.START_DATE_AFTER_END_DATE_EXCEPTION);
    }

    // 요청 dto에 있지만 db에는 없는 팀원을 추가
    private void addProjectUser(Project project, Set<Long> requestMemberIdList) {
        Set<Long> existingMemberIdList = project.getMemberIdList();
        for(Long requestMemberId : requestMemberIdList)
            if(!existingMemberIdList.contains(requestMemberId))
                project.getProjectUserList().add(
                        ProjectUser.builder()
                                .project(project)
                                .userId(requestMemberId)
                                .role(Role.MEMBER)
                                .build()
                );
    }

    // 요청 dto에 없지만 db에는 있는 팀원을 삭제
    private void deleteProjectUser(Project project, Set<Long> requestMemberIdList) {
        project.getProjectUserList()
                .removeIf(projectUser -> !requestMemberIdList.contains(projectUser.getUserId()));
    }
}
