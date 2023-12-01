package com.mju.management.domain.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mju.management.BaseApiTest;
import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.schedule.dto.reqeust.CreateScheduleRequestDto;
import com.mju.management.domain.schedule.infrastructure.Schedule;
import com.mju.management.domain.schedule.infrastructure.ScheduleRepository;
import com.mju.management.global.config.jwtInterceptor.JwtContextHolder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ScheduleApiTest extends BaseApiTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    private final String scheduleContent = "소코아 프로젝트 스프린트 #1";
    private final String scheduleStartDate = "2023-11-01";
    private final String scheduleEndDate = "2023-11-07";

    @DisplayName("일정 생성 성공")
    @Test
    public void createSchedule_Success() throws Exception {
        //given

        JwtContextHolder.setUserId(leaderId);

        Project project = createProject(leaderId);

        CreateScheduleRequestDto createScheduleRequestDto = CreateScheduleRequestDto
                .builder()
                .content(scheduleContent)
                .startDate(scheduleStartDate)
                .endDate(scheduleEndDate)
                .build();

        String requestBody = objectMapper.writeValueAsString(createScheduleRequestDto);

        String url = "/api/projects/{projectId}/schedules";

        //when
        ResultActions result = mockMvc.perform(post(url, project.getProjectId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));
        //then
        result.andExpect(jsonPath("$.code").value(200));

        List<Schedule> scheduleList = scheduleRepository.findAll();
        assertThat(scheduleList.size()).isEqualTo(1);
        Schedule schedule = scheduleList.get(0);
        assertThat(schedule.getContent()).isEqualTo(scheduleContent);
        assertThat(schedule.getStartDate()).isEqualTo(scheduleStartDate);
        assertThat(schedule.getEndDate()).isEqualTo(scheduleEndDate);
    }

    @Test
    @DisplayName("일정 생성 실패: 프로젝트가 존재하지 않음")
    public void createSchedule_Fail_NonExistentProject() throws Exception {
        //given
        JwtContextHolder.setUserId(leaderId);

        CreateScheduleRequestDto createScheduleRequestDto = CreateScheduleRequestDto
                .builder()
                .content(scheduleContent)
                .startDate(scheduleStartDate)
                .endDate(scheduleEndDate)
                .build();

        String requestBody = objectMapper.writeValueAsString(createScheduleRequestDto);

        String url = "/api/projects/{projectId}/schedules";

        //when
        ResultActions result = mockMvc.perform(post(url, 1)
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(jsonPath("$.code").value(5008));
        assertThat(scheduleRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("일정 생성 실패: 요청자가 프로젝트 팀장이 아님")
    public void createSchedule_Fail_UnauthorizedAccess() throws Exception {
        //given
        JwtContextHolder.setUserId(memberId);

        Project project = createProject(leaderId);

        CreateScheduleRequestDto createScheduleRequestDto = CreateScheduleRequestDto
                .builder()
                .content(scheduleContent)
                .startDate(scheduleStartDate)
                .endDate(scheduleEndDate)
                .build();

        String requestBody = objectMapper.writeValueAsString(createScheduleRequestDto);

        String url = "/api/projects/{projectId}/schedules";

        //when
        ResultActions result = mockMvc.perform(post(url, project.getProjectId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(jsonPath("$.code").value(8000));
        assertThat(scheduleRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("일정 생성 실패: 종료일이 시작일보다 앞섬")
    public void createSchedule_Fail_StartDateAfterEndDate() throws Exception {
        //given
        JwtContextHolder.setUserId(leaderId);

        Project project = createProject(leaderId);

        CreateScheduleRequestDto createScheduleRequestDto = CreateScheduleRequestDto
                .builder()
                .content(scheduleContent)
                .startDate(scheduleEndDate)
                .endDate(scheduleStartDate)
                .build();

        String requestBody = objectMapper.writeValueAsString(createScheduleRequestDto);

        String url = "/api/projects/{projectId}/schedules";

        //when
        ResultActions result = mockMvc.perform(post(url, project.getProjectId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(jsonPath("$.code").value(6002));
        assertThat(scheduleRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("일정 생성 실패: 일정이 프로젝트 기간에 벗어남")
    public void createSchedule_Fail_OutOfProjectScheduleRange() throws Exception {
        //given
        JwtContextHolder.setUserId(leaderId);

        Project project = createProject(leaderId);

        CreateScheduleRequestDto createScheduleRequestDto = CreateScheduleRequestDto
                .builder()
                .content(scheduleContent)
                .startDate(scheduleStartDate)
                .endDate("2030-10-01")
                .build();

        String requestBody = objectMapper.writeValueAsString(createScheduleRequestDto);

        String url = "/api/projects/{projectId}/schedules";

        //when
        ResultActions result = mockMvc.perform(post(url, project.getProjectId())
                .contentType(APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(jsonPath("$.code").value(6004));
        assertThat(scheduleRepository.findAll()).isEmpty();
    }
}
