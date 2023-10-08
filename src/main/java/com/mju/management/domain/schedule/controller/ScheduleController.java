package com.mju.management.domain.schedule.controller;

import com.mju.management.domain.schedule.infrastructure.Schedule;
import com.mju.management.domain.schedule.service.ScheduleService;
import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.global.service.ResponseService;
import com.mju.management.domain.schedule.dto.reqeust.CreateScheduleRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Tag(name = "일정 CRDU API", description = "일정 CRUD API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ResponseService responseService;

    //일정 등록
    @Operation(summary = "일정 등록", description = "일정 등록 API")
    @ResponseStatus(OK)
    @PostMapping()
    public CommonResult createSchedule(@Valid @RequestBody CreateScheduleRequestDto createScheduleRequestDto){
        scheduleService.createSchedule(createScheduleRequestDto);
        return responseService.getSuccessfulResult();
    }

    //일정 목록 조회
    @Operation(summary = "일정 목록 가져오기", description = "일정 목록 가져오기 API")
    @ResponseStatus(OK)
    @GetMapping()
    public CommonResult getScheduleList() {
        List<Schedule> scheduleList = scheduleService.getScheduleList();
        return responseService.getListResult(scheduleList);
    }

    //일정 하나 조회
    @Operation(summary = "일정 가져오기", description = "일정 가져오기 API")
    @ResponseStatus(OK)
    @GetMapping("/{scheduleId}")
    public CommonResult getSchedule(@PathVariable Long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        return responseService.getSingleResult(schedule);
    }

    //일정 수정
    @Operation(summary = "일정 수정", description = "일정 수정 API")
    @ResponseStatus(OK)
    @PutMapping("/{scheduleId}")
    public CommonResult updateSchedule(@PathVariable Long scheduleId, @Valid @RequestBody CreateScheduleRequestDto updateScheduleRequestDto) {
        scheduleService.updateSchedule(scheduleId, updateScheduleRequestDto);
        return responseService.getSuccessfulResult();
    }
    //일정 삭제
    @Operation(summary = "일정 삭제", description = "일정 삭제 API")
    @ResponseStatus(OK)
    @DeleteMapping("/{scheduleId}")
    public CommonResult deleteSchedule(@PathVariable Long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return responseService.getSuccessfulResult();
    }
}
