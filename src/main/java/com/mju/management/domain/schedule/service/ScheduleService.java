package com.mju.management.domain.schedule.service;


import com.mju.management.domain.schedule.dto.reqeust.CreateScheduleRequestDto;
import com.mju.management.domain.schedule.infrastructure.Schedule;

import java.util.List;

public interface ScheduleService {
    void createSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    List<Schedule> getScheduleList();
    Schedule getSchedule(Long scheduleIndex);
    void updateSchedule(Long scheduleIndex, CreateScheduleRequestDto updateScheduleRequestDto);
    void deleteSchedule(Long scheduleIndex);
}
