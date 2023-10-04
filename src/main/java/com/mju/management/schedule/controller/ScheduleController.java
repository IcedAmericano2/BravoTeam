package com.mju.management.schedule.controller;

import com.mju.management.schedule.service.ScheduleService;
import com.mju.management.domain.model.Result.CommonResult;
import com.mju.management.schedule.infrastructure.Schedule;
import com.mju.management.domain.service.ResponseService;
import com.mju.management.schedule.dto.CreateScheduleRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bravoTeam-service/schedules")
public class ScheduleController {
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    ResponseService responseService;

    @GetMapping("/ping")
    public String ping() {
        return "fong";
    }

    //일정 등록
    @PostMapping()
    public CommonResult createSchedule(@RequestBody CreateScheduleRequestDto createScheduleRequestDto){
        scheduleService.createSchedule(createScheduleRequestDto);
        return responseService.getSuccessfulResult();
    }

    //일정 전체 조회
    @GetMapping()
    public CommonResult getScheduleList() {
        List<Schedule> scheduleList = scheduleService.getScheduleList();
        return responseService.getListResult(scheduleList);
    }

    //일정 하나 조회
    @GetMapping("/{scheduleIndex}")
    public CommonResult getSchedule(@PathVariable Long scheduleIndex) {
        Schedule schedule = scheduleService.getSchedule(scheduleIndex);
        return responseService.getSingleResult(schedule);
    }

    //일정 수정
    @PutMapping("/{scheduleIndex}")
    public CommonResult updateSchedule(@PathVariable Long scheduleIndex, @RequestBody CreateScheduleRequestDto updateScheduleRequestDto) {
        scheduleService.updateSchedule(scheduleIndex, updateScheduleRequestDto);
        return responseService.getSuccessfulResult();
    }
    //일정 삭제
    @DeleteMapping("/{scheduleIndex}")
    public CommonResult deleteSchedule(@PathVariable Long scheduleIndex) {
        scheduleService.deleteSchedule(scheduleIndex);
        return responseService.getSuccessfulResult();
    }
}
