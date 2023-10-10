package com.mju.management.domain.project.contoller;

import com.mju.management.domain.project.service.ProjectService;
import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.user.service.UserServiceImpl;
import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.global.service.ResponseService;
import com.mju.management.domain.project.dto.ProjectRegisterDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project")
@Tag(name = "프로젝트 CRUD API", description = "프로젝트 CRUD API")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @Autowired
    ResponseService responseService;
    @Autowired
    UserServiceImpl userService;

    @GetMapping("/ping")
    @Operation(summary = "Ping 테스트", description = "서버 피드백 확인용")
    public String ping() {
        return "pong";
    }

    //프로젝트 등록
    @PostMapping()
    @Operation(summary = "프로젝트 등록", description = "신규 프로젝트를 등록하는 API")
    public CommonResult registerProject(@RequestBody ProjectRegisterDto projectRegisterDto/*, HttpServletRequest request*/) {
//        String userId = userService.getUserId(request);
//        userService.checkUserType(userId, "");
        projectService.registerProject(/*userId, */projectRegisterDto);
        return responseService.getSuccessfulResult();
    }
    //프로젝트 전체 조회
    @GetMapping()
    @Operation(summary = "프로젝트 목록 가져오기", description = "프로젝트 목록을 가져오는 API")
    public CommonResult getProject() {
        List<Project> project = projectService.getProject();
        return responseService.getListResult(project);
    }
    //프로젝트 수정
    @PutMapping("/{projectIndex}")
    @Operation(summary = "프로젝트 수정하기", description = "기존 프로젝트를 수정하는 API")
    public CommonResult updateProject(@PathVariable Long projectIndex, @RequestBody ProjectRegisterDto projectRegisterDto){
        projectService.updateProject(projectIndex, projectRegisterDto);
        return responseService.getSuccessfulResult();
    }
    //프로젝트 삭제
    @DeleteMapping("/{projectIndex}")
    @Operation(summary = "프로젝트 삭제하기", description = "프로젝트를 삭제하는 API")
    public CommonResult deleteProject(@PathVariable Long projectIndex){
        projectService.deleteProject(projectIndex);
        return responseService.getSuccessfulResult();
    }
    //프로젝트 완료 표시
    @GetMapping("/{projectIndex}")
    @Operation(summary = "완료된 프로젝트 표시하기", description = "완료된 프로젝트를 표시하는 API")
    public CommonResult finishCheckList(@PathVariable Long projectIndex){
        projectService.finishProject(projectIndex);
        return responseService.getSuccessfulResult();
    }
}
