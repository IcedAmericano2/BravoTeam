package com.mju.bravoTeam.application;

import com.mju.bravoTeam.domain.model.Exception.ExceptionList;
import com.mju.bravoTeam.domain.model.Exception.NonExistentException;
import com.mju.bravoTeam.domain.model.Schedule;
import com.mju.bravoTeam.domain.repository.ScheduleRepository;
import com.mju.bravoTeam.presentation.dto.CreateScheduleRequestDto;
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
        scheduleRepository.save(createScheduleRequestDto.toEntity());
    }

    @Override
    public List<Schedule> getScheduleList() {
        List<Schedule> scheduleList = scheduleRepository.findAll();
        if (!scheduleList.isEmpty()) return scheduleList;
        else throw new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULELIST);
    }

    @Override
    public Schedule getSchedule(Long scheduleIndex) {
        return scheduleRepository.findById(scheduleIndex)
                .orElseThrow(()-> new NonExistentException(ExceptionList.NON_EXISTENT_SCHEDULE));
    }

    @Override
    @Transactional
    public void updateSchedule(Long scheduleIndex, CreateScheduleRequestDto updateScheduleRequestDto) {
        getSchedule(scheduleIndex).update(
                updateScheduleRequestDto.getTitle(),
                updateScheduleRequestDto.getDate()
        );
    }

    @Override
    public void deleteSchedule(Long scheduleIndex) {
        scheduleRepository.delete(getSchedule(scheduleIndex));
    }
}
