package com.mju.management.presentation.controller;

import com.mju.management.presentation.service.CheckListService;
import com.mju.management.presentation.domain.CheckList;
import com.mju.management.domain.model.Result.CommonResult;
import com.mju.management.domain.service.ResponseService;
import com.mju.management.presentation.dto.CheckListRegisterDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bravoTeam-service/checklist")
public class CheckListController {
    @Autowired
    CheckListService checkListService;
    @Autowired
    ResponseService responseService;
    @GetMapping("/ping")
    public String ping() {
        return "fong";
    }

    //체크박스 등록
    @PostMapping("/register")
    public CommonResult registerCheckList(@RequestBody CheckListRegisterDto checkListRegisterDto){
        checkListService.registerCheckList(checkListRegisterDto);
        return responseService.getSuccessfulResult();
    }

    //체크박스 전체 조회
    @GetMapping("/show")
    public CommonResult showCheckList() {
        List<CheckList> checkListList = checkListService.getCheckList();
        CommonResult commonResult = responseService.getListResult(checkListList);
        return commonResult;
    }
    //체크박스 하나만 선택
    @GetMapping("/show/{checkListIndex}")
    public CommonResult showCheckListOne(@PathVariable Long checkListIndex) {
        checkListService.showCheckListOne(checkListIndex);
        return responseService.getSuccessfulResult();
    }
    //체크박스 수정
    @PutMapping("/update/{checkListIndex}")
    public CommonResult updateCheckList(@PathVariable Long checkListIndex, @RequestBody CheckListRegisterDto checkListRegisterDto) {
        checkListService.updateCheckList(checkListIndex, checkListRegisterDto);
        return responseService.getSuccessfulResult();
    }
    //체크박스 삭제
    @DeleteMapping("/delete/{checkListIndex}")
    public CommonResult deleteCheckList(@PathVariable Long checkListIndex) {
        checkListService.deleteCheckList(checkListIndex);
        return responseService.getSuccessfulResult();
    }

    //체크박스 클릭(완료 표시)
    @GetMapping("/finish")
    public CommonResult finishCheckList(@PathVariable Long checkListIndex) {
        checkListService.finishCheckList(checkListIndex);
        return responseService.getSuccessfulResult();
    }
}
