package com.mju.management.domain.project.contoller;

import com.mju.management.domain.project.dto.response.GetProjectListResponseDto;
import com.mju.management.domain.project.dto.response.GetProjectResponseDto;
import com.mju.management.domain.project.service.ProjectService;
import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.global.service.ResponseService;
import com.mju.management.domain.project.dto.reqeust.ProjectRegisterRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "프로젝트 CRUD API", description = "프로젝트 CRUD API")
@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {
    private final ProjectService projectService;
    private final ResponseService responseService;

    @Operation(summary = "핑 테스트")
    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    //프로젝트 등록
    @Operation(summary = "프로젝트 생성")
    @PostMapping()
    public CommonResult registerProject(@Valid @RequestBody ProjectRegisterRequestDto projectRegisterRequestDto) {
        projectService.registerProject(projectRegisterRequestDto);
        return responseService.getSuccessfulResult();
    }
    //프로젝트 전체 조회
    @Operation(summary = "프로젝트 전체목록 조회")
    @GetMapping()
    public CommonResult getProjectList() {
        List<GetProjectListResponseDto> projectList = projectService.getProjectList();
        return responseService.getListResult(projectList);
    }

    @Operation(summary = "내가 속한 프로젝트 목록 조회")
    @GetMapping("/me")
    public CommonResult getMyProjectList() {
        List<GetProjectListResponseDto> myProjectList = projectService.getMyProjectList();
        return responseService.getListResult(myProjectList);
    }

    // 프로젝트 상세 조회
    @Operation(summary = "프로젝트 상세 조회")
    @GetMapping("/{projectIndex}")
    public CommonResult getProject(@PathVariable Long projectIndex) {
        GetProjectResponseDto project = projectService.getProject(projectIndex);
        return responseService.getSingleResult(project);
    }

    //프로젝트 수정
    @Operation(summary = "프로젝트 수정")
    @PutMapping("/{projectIndex}")
    public CommonResult updateProject(@PathVariable Long projectIndex,
                                      @Valid @RequestBody ProjectRegisterRequestDto projectUpdateRequestDto){
        projectService.updateProject(projectIndex, projectUpdateRequestDto);
        return responseService.getSuccessfulResult();
    }
    //프로젝트 삭제
    @Operation(summary = "프로젝트 삭제")
    @DeleteMapping("/{projectIndex}")
    public CommonResult deleteProject(@PathVariable Long projectIndex){
        projectService.deleteProject(projectIndex);
        return responseService.getSuccessfulResult();
    }
    //프로젝트 완료 표시
    @Operation(summary = "프로젝트 완료 표시")
    @PutMapping("/{projectIndex}/finish")
    public CommonResult finishCheckList(@PathVariable Long projectIndex){
        projectService.finishProject(projectIndex);
        return responseService.getSuccessfulResult();
    }
}
