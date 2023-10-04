package com.mju.management.presentation.service;


import com.mju.management.presentation.domain.Schedule;
import com.mju.management.presentation.dto.CreateScheduleRequestDto;

import java.util.List;

public interface ScheduleService {
    public void createSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    public List<Schedule> getScheduleList();
    public Schedule getSchedule(Long scheduleIndex);
    public void updateSchedule(Long scheduleIndex, CreateScheduleRequestDto updateScheduleRequestDto);
    public void deleteSchedule(Long scheduleIndex);
}
