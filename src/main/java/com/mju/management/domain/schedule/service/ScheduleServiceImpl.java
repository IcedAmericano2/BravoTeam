package com.mju.management.domain.schedule.service;



import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import com.mju.management.domain.schedule.dto.response.GetScheduleResponseDto;
import com.mju.management.domain.schedule.infrastructure.Schedule;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.StartDateAfterEndDateException;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.domain.schedule.dto.reqeust.CreateScheduleRequestDto;
import com.mju.management.domain.schedule.infrastructure.ScheduleRepository;
import com.mju.management.global.model.Exception.OutOfProjectScheduleRangeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final ProjectRepository projectRepository;

    @Override
    public void createSchedule(Long projectId, CreateScheduleRequestDto createScheduleRequestDto) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        validateSchedule(createScheduleRequestDto, project);
        scheduleRepository.save(createScheduleRequestDto.toEntity(project));
    }

    @Override
    public List<GetScheduleResponseDto> getScheduleList(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new NonExistentException(ExceptionList.NON_EXISTENT_PROJECT));
        List<GetScheduleResponseDto> scheduleList = scheduleRepository.findAllByProject(project)
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
        Schedule schedule =  scheduleRepository.findByIdWithProject(scheduleId)
                .orElseThrow(()-> new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULE));
        validateSchedule(updateScheduleRequestDto, schedule.getProject());
        schedule.update(updateScheduleRequestDto);
    }

    @Override
    public void deleteSchedule(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(()-> new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULE));
        scheduleRepository.delete(schedule);
    }

    public void validateSchedule(CreateScheduleRequestDto createScheduleRequestDto, Project project){
        LocalDate startDate = createScheduleRequestDto.readStartDateAsLocalDateType();
        LocalDate endDate = createScheduleRequestDto.readEndDateAsLocalDateType();
        if(startDate.isAfter(endDate))
            throw new StartDateAfterEndDateException(ExceptionList.START_DATE_AFTER_END_DATE_EXCEPTION);
        if(startDate.isBefore(project.getStartDate()) || endDate.isAfter(project.getFinishDate()))
            throw new OutOfProjectScheduleRangeException(ExceptionList.OUT_OF_PROJECT_SCHEDULE_RANGE);
    }
}
