package com.mju.management.presentation.service;



import com.mju.management.domain.model.Exception.ExceptionList;
import com.mju.management.domain.model.Exception.InvalidDateRangeException;
import com.mju.management.domain.model.Exception.NonExistentException;
import com.mju.management.presentation.infrastructure.Schedule;
import com.mju.management.presentation.dto.CreateScheduleRequestDto;
import com.mju.management.presentation.infrastructure.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleRepository scheduleRepository;

    @Override
    public void createSchedule(CreateScheduleRequestDto createScheduleRequestDto) {
        validateDateRange(createScheduleRequestDto);
        scheduleRepository.save(createScheduleRequestDto.toEntity());
    }

    @Override
    public List<Schedule> getScheduleList() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        if (scheduleList.isEmpty()) throw new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULELIST);
        return scheduleList;
    }

    @Override
    public Schedule getSchedule(Long scheduleIndex) {
        return scheduleRepository.findById(scheduleIndex)
                .orElseThrow(()-> new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULE));
    }

    @Override
    @Transactional
    public void updateSchedule(Long scheduleIndex, CreateScheduleRequestDto updateScheduleRequestDto) {
        validateDateRange(updateScheduleRequestDto);
        getSchedule(scheduleIndex).update(updateScheduleRequestDto);
    }

    @Override
    public void deleteSchedule(Long scheduleIndex) {
        scheduleRepository.delete(getSchedule(scheduleIndex));
    }

    public void validateDateRange(CreateScheduleRequestDto createScheduleRequestDto){
        if(createScheduleRequestDto.getStartDate().isAfter(createScheduleRequestDto.getEndDate()))
            throw new InvalidDateRangeException(ExceptionList.INVALID_DATE_RANGE_EXCEPTION);
    }
}