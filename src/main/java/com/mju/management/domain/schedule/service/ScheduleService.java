package com.mju.management.domain.schedule.service;


import com.mju.management.domain.schedule.dto.CreateScheduleRequestDto;
import com.mju.management.domain.schedule.infrastructure.Schedule;

import java.util.List;

public interface ScheduleService {
    public void createSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    public List<Schedule> getScheduleList();
    public Schedule getSchedule(Long scheduleIndex);
    public void updateSchedule(Long scheduleIndex, CreateScheduleRequestDto updateScheduleRequestDto);
    public void deleteSchedule(Long scheduleIndex);
}
