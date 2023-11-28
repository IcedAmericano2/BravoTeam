package com.mju.management.domain.project;

import com.mju.management.BaseApiTest;
import com.mju.management.domain.project.dto.reqeust.CreateProjectRequestDto;
import com.mju.management.domain.project.infrastructure.*;
import com.mju.management.global.config.jwtInterceptor.JwtContextHolder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ProjectApiTest extends BaseApiTest {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectUserRepository projectUserRepository;

    private final Long userId = 1L;
    private final Long memberId = 2L;
    private final String name = "소코아 프로젝트";
    private final String description = "소코아 프로젝트입니다.";
    private final String startDate = "2023-09-01";
    private final String finishDate = "2023-12-15";

    public Long createProject(){
        // create Project
        Project project = projectRepository.save(Project
                .builder()
                .name(name)
                .description(description)
                .startDate(LocalDate.parse(startDate))
                .finishDate(LocalDate.parse(finishDate))
                .build()
        );

        // create ProjectUser(leader)
        projectUserRepository.save(ProjectUser
                .builder()
                .userId(userId)
                .project(project)
                .role(Role.LEADER)
                .build()
        );

        // create ProjectUser(member)
        projectUserRepository.save(ProjectUser
                .builder()
                .userId(memberId)
                .project(project)
                .role(Role.LEADER)
                .build()
        );

        return project.getProjectId();
    }

    @AfterEach
    public void tearDown() {
        // delete all Project
        projectRepository.deleteAll();
        JwtContextHolder.clear();
    }

    @DisplayName("프로젝트 생성을 성공한다.")
    @Test
    public void createProject_Success() throws Exception {
        //given
        JwtContextHolder.setUserId(userId);

        String url = "/api/projects";

        Set<Long> memberIdList = new HashSet<>();
        memberIdList.add(memberId);

        CreateProjectRequestDto createProjectRequestDto = CreateProjectRequestDto
                .builder()
                .name(name)
                .description(description)
                .startDate(startDate)
                .finishDate(finishDate)
                .memberIdList(memberIdList)
                .build();

        String requestBody = objectMapper.writeValueAsString(createProjectRequestDto);

        //when
        ResultActions result = mockMvc.perform(post(url)
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(jsonPath("$.code").value(200));

        List<Project> projectList = projectRepository.findAll();

        assertThat(projectList.size()).isEqualTo(1);
        Project project = projectList.get(0);
        assertThat(project.getName()).isEqualTo(name);
        assertThat(project.getDescription()).isEqualTo(description);
        assertThat(project.getStartDate()).isEqualTo(startDate);
        assertThat(project.getFinishDate()).isEqualTo(finishDate);
        assertThat(projectUserRepository.findByProjectAndUserId(project, userId).isPresent())
                .isEqualTo(true);
        assertThat(projectUserRepository.findByProjectAndUserId(project, memberId).isPresent())
                .isEqualTo(true);
    }

    @DisplayName("프로젝트 전체목록 조회를 성공한다.")
    @Test
    public void getProjectList_Success() throws Exception {
        //given
        int count = 10;
        for(int i = 0; i < count; i++) createProject();
        String url = "/api/projects";

        //when
        ResultActions result = mockMvc.perform(get(url)
                .accept(APPLICATION_JSON_VALUE));

        //then
        result.andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.list").value(Matchers.hasSize(count)));
    }

    @DisplayName("내가 속한 프로젝트 목록 조회를 성공한다.")
    @Test
    public void getMyProjectList_Success() throws Exception {
        //given
        JwtContextHolder.setUserId(userId);

        int count = 10;
        for(int i = 0; i < count; i++) createProject();

        JwtContextHolder.setUserId(memberId);

        String url = "/api/projects/me";

        //when
        ResultActions result = mockMvc.perform(get(url)
                .accept(APPLICATION_JSON_VALUE));

        //then
        result.andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.list").value(Matchers.hasSize(count)));
    }

    @DisplayName("프로젝트 상세 조회를 성공한다.")
    @Test
    public void getProject_Success() throws Exception {
        //given
        JwtContextHolder.setUserId(userId);

        Long projectId = createProject();

        String url = "/api/projects/{projectId}";

        //when
        ResultActions result = mockMvc.perform(get(url, projectId)
                .accept(APPLICATION_JSON_VALUE));

        //then
        result.andExpect(jsonPath("$.code").value(200))
                .andExpect(jsonPath("$.data.projectId").value(projectId))
                .andExpect(jsonPath("$.data.name").value(name))
                .andExpect(jsonPath("$.data.description").value(description))
                .andExpect(jsonPath("$.data.startDate").value(startDate))
                .andExpect(jsonPath("$.data.finishDate").value(finishDate))
                .andExpect(jsonPath("$.data.leaderAndMemberList[0].userId").value(userId))
                .andExpect(jsonPath("$.data.leaderAndMemberList[1].userId").value(memberId));

    }

    @DisplayName("프로젝트 수정을 성공한다.")
    @Test
    public void updateProject_Success() throws Exception {
        //given
        JwtContextHolder.setUserId(userId);

        Long projectId = createProject();

        String url = "/api/projects/{projectId}";

        String name = "스튜디오아이 프로젝트";
        String description = "스튜디오아이 프로젝트입니다.";
        String startDate = "2023-10-01";
        String finishDate = "2023-11-11";
        Long newMemberId = 3L;

        Set<Long> memberIdList = new HashSet<>();
        memberIdList.add(newMemberId);

        CreateProjectRequestDto updateProjectRequestsDto = CreateProjectRequestDto
                .builder()
                .name(name)
                .description(description)
                .startDate(startDate)
                .finishDate(finishDate)
                .memberIdList(memberIdList)
                .build();

        //when
        ResultActions result = mockMvc.perform(put(url, projectId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(updateProjectRequestsDto)));

        //then
        result.andExpect(jsonPath("$.code").value(200));

        Project project = projectRepository.findById(projectId).get();

        assertThat(project.getName()).isEqualTo(name);
        assertThat(project.getDescription()).isEqualTo(description);
        assertThat(project.getStartDate()).isEqualTo(startDate);
        assertThat(project.getFinishDate()).isEqualTo(finishDate);
        assertThat(projectUserRepository.findByProjectAndUserId(project, userId).isPresent())
                .isEqualTo(true);
        assertThat(projectUserRepository.findByProjectAndUserId(project, newMemberId).isPresent())
                .isEqualTo(true);
    }

    @DisplayName("프로젝트 삭제를 성공한다.")
    @Test
    public void deleteProject_Success() throws Exception {
        //given
        JwtContextHolder.setUserId(userId);

        Long projectId = createProject();

        String url = "/api/projects/{projectId}";

        //when
        ResultActions result = mockMvc.perform(delete(url, projectId));

        //then
        result.andExpect(jsonPath("$.code").value(200));
        assertThat(projectRepository.findById(projectId)).isEmpty();

    }

    @DisplayName("프로젝트 완료를 성공한다.")
    @Test
    public void finishProject_Success() throws Exception {
        //given
        JwtContextHolder.setUserId(userId);

        Long projectId = createProject();

        String url = "/api/projects/{projectId}/finish";

        //when
        ResultActions result = mockMvc.perform(put(url, projectId));

        //then
        result.andExpect(jsonPath("$.code").value(200));
        assertThat(projectRepository.findById(projectId).get().isChecked()).isTrue();


    }

}
