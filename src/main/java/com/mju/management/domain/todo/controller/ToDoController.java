package com.mju.management.domain.todo.controller;

import com.mju.management.domain.todo.dto.ToDoRegisterDto;
import com.mju.management.domain.todo.service.ToDoService;
import com.mju.management.domain.todo.infrastructure.ToDoEntity;
import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.global.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@Tag(name = "ToDoController", description = "할 일 관련 API")
public class ToDoController {
    @Autowired
    ToDoService toDoService;
    @Autowired
    ResponseService responseService;
    @GetMapping("/ping")
    @Operation(summary = "Ping 테스트", description = "서버 피드백 확인용")
    public String ping() {
        return "fong";
    }

    //체크박스 등록
    @PostMapping()
    @Operation(summary = "할일 등록", description = "할일 등록 api")
    public CommonResult registerToDo(@RequestBody ToDoRegisterDto toDoRegisterDto){
        toDoService.registerToDo(toDoRegisterDto);
        return responseService.getSuccessfulResult();
    }

    //체크박스 전체 조회
    @GetMapping()
    @Operation(summary = "할일 조회", description = "할일 조회 api")
    public CommonResult showToDo() {
        List<ToDoEntity> toDoEntity = toDoService.getToDo();
        CommonResult commonResult = responseService.getListResult(toDoEntity);
        return commonResult;
    }
    //체크박스 하나만 선택
    @GetMapping("/{todoIndex}")
    @Operation(summary = "할일 선택 조회", description = "할일 선택 조회 api")
    public CommonResult showToDoOne(@PathVariable Long todoIndex) {
        toDoService.showToDoOne(todoIndex);
        return responseService.getSuccessfulResult();
    }
    //체크박스 수정
    @PutMapping("/{todoIndex}")
    @Operation(summary = "할일 업데이트", description = "할일 업데이트 api")
    public CommonResult updateToDo(@PathVariable Long todoIndex, @RequestBody ToDoRegisterDto toDoRegisterDto) {
        toDoService.updateToDo(todoIndex, toDoRegisterDto);
        return responseService.getSuccessfulResult();
    }
    //체크박스 삭제
    @DeleteMapping("/{todoIndex}")
    @Operation(summary = "할일 삭제", description = "할일 삭제 api")
    public CommonResult deleteToDo(@PathVariable Long todoIndex) {
        toDoService.deleteToDo(todoIndex);
        return responseService.getSuccessfulResult();
    }

    //체크박스 클릭(완료 표시)
    @GetMapping("/finish/{todoIndex}")
    @Operation(summary = "할일 완료 표시", description = "할일 완료 표시 api")
    public CommonResult finishToDo(@PathVariable Long todoIndex) {
        toDoService.finishToDo(todoIndex);
        return responseService.getSuccessfulResult();
    }
}
