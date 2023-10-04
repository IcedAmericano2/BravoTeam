package com.mju.management.domain.todo.controller;

import com.mju.management.domain.todo.dto.ToDoRegisterDto;
import com.mju.management.domain.todo.infrastructure.ToDoEntity;
import com.mju.management.domain.todo.service.ToDoService;
import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.global.service.ResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bravoTeam-service/checklist")
public class ToDoController {
    @Autowired
    ToDoService toDoService;
    @Autowired
    ResponseService responseService;
    @GetMapping("/ping")
    public String ping() {
        return "fong";
    }

    //체크박스 등록
    @PostMapping("/register")
    public CommonResult registerCheckList(@RequestBody ToDoRegisterDto toDoRegisterDto){
        toDoService.registerCheckList(toDoRegisterDto);
        return responseService.getSuccessfulResult();
    }

    //체크박스 전체 조회
    @GetMapping("/show")
    public CommonResult showCheckList() {
        List<ToDoEntity> toDoEntity = toDoService.getCheckList();
        CommonResult commonResult = responseService.getListResult(toDoEntity);
        return commonResult;
    }
    //체크박스 하나만 선택
    @GetMapping("/show/{checkListIndex}")
    public CommonResult showCheckListOne(@PathVariable Long checkListIndex) {
        toDoService.showCheckListOne(checkListIndex);
        return responseService.getSuccessfulResult();
    }
    //체크박스 수정
    @PutMapping("/update/{checkListIndex}")
    public CommonResult updateCheckList(@PathVariable Long checkListIndex, @RequestBody ToDoRegisterDto toDoRegisterDto) {
        toDoService.updateCheckList(checkListIndex, toDoRegisterDto);
        return responseService.getSuccessfulResult();
    }
    //체크박스 삭제
    @DeleteMapping("/delete/{checkListIndex}")
    public CommonResult deleteCheckList(@PathVariable Long checkListIndex) {
        toDoService.deleteCheckList(checkListIndex);
        return responseService.getSuccessfulResult();
    }

    //체크박스 클릭(완료 표시)
    @GetMapping("/finish")
    public CommonResult finishCheckList(@PathVariable Long checkListIndex) {
        toDoService.finishCheckList(checkListIndex);
        return responseService.getSuccessfulResult();
    }
}
