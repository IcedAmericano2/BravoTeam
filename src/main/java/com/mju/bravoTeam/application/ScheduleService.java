package com.mju.bravoTeam.application;

import com.mju.bravoTeam.domain.model.Schedule;
import com.mju.bravoTeam.presentation.dto.CreateScheduleRequestDto;

import java.util.List;

public interface ScheduleService {
    public void createSchedule(CreateScheduleRequestDto createScheduleRequestDto);
    public List<Schedule> getScheduleList();
    public Schedule getSchedule(Long scheduleIndex);
    public void updateSchedule(Long scheduleIndex, CreateScheduleRequestDto updateScheduleRequestDto);
    public void deleteSchedule(Long scheduleIndex);
}
