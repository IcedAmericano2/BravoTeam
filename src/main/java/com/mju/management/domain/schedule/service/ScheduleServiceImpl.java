package com.mju.management.domain.schedule.service;



import com.mju.management.domain.schedule.infrastructure.Schedule;
import com.mju.management.global.model.Exception.ExceptionList;
import com.mju.management.global.model.Exception.InvalidDateRangeException;
import com.mju.management.global.model.Exception.NonExistentException;
import com.mju.management.domain.schedule.dto.CreateScheduleRequestDto;
import com.mju.management.domain.schedule.infrastructure.ScheduleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService{
    private final ScheduleJpaRepository scheduleJpaRepository;

    @Override
    public void createSchedule(CreateScheduleRequestDto createScheduleRequestDto) {
        validateDateRange(createScheduleRequestDto);
        scheduleJpaRepository.save(createScheduleRequestDto.toEntity());
    }

    @Override
    public List<Schedule> getScheduleList() {
        List<Schedule> scheduleList = scheduleJpaRepository.findAll();
        if (scheduleList.isEmpty()) throw new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULELIST);
        return scheduleList;
    }

    @Override
    public Schedule getSchedule(Long scheduleIndex) {
        return scheduleJpaRepository.findById(scheduleIndex)
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
        scheduleJpaRepository.delete(getSchedule(scheduleIndex));
    }

    public void validateDateRange(CreateScheduleRequestDto createScheduleRequestDto){
        if(createScheduleRequestDto.getStartDate().isAfter(createScheduleRequestDto.getEndDate()))
            throw new InvalidDateRangeException(ExceptionList.INVALID_DATE_RANGE_EXCEPTION);
    }
}
