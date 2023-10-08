package com.mju.management.domain.schedule.service;



import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import com.mju.management.domain.schedule.dto.response.GetScheduleResponseDto;
import com.mju.management.domain.schedule.infrastructure.Schedule;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.InvalidDateRangeException;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.domain.schedule.dto.reqeust.CreateScheduleRequestDto;
import com.mju.management.domain.schedule.infrastructure.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void createSchedule(Long projectId, CreateScheduleRequestDto createScheduleRequestDto) {
        validateDateRange(createScheduleRequestDto);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        scheduleRepository.save(createScheduleRequestDto.toEntity(project));
    }

    @Override
    public List<GetScheduleResponseDto> getScheduleList(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        List<GetScheduleResponseDto> scheduleList = scheduleRepository.findByProject(project)
                .stream()
                .map(schedule -> GetScheduleResponseDto.from(schedule))
                .collect(Collectors.toList());
        if (scheduleList.isEmpty()) throw new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULELIST);
        return scheduleList;
    }

    @Override
    public GetScheduleResponseDto getSchedule(Long scheduleId) {
        Schedule schedule =  scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULE));
        return GetScheduleResponseDto.from(schedule);
    }

    @Override
    @Transactional
    public void updateSchedule(Long scheduleId, CreateScheduleRequestDto updateScheduleRequestDto) {
        validateDateRange(updateScheduleRequestDto);
        scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULE))
                .update(updateScheduleRequestDto);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULE));
        scheduleRepository.delete(schedule);
    }

    public void validateDateRange(CreateScheduleRequestDto createScheduleRequestDto){
        if(createScheduleRequestDto.readStartDateAsLocalDateType()
                .isAfter(createScheduleRequestDto.readEndDateAsLocalDateType()))
            throw new InvalidDateRangeException(ExceptionList.INVALID_DATE_RANGE);
    }
}
