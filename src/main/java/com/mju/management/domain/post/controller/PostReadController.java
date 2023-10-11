package com.mju.management.domain.post.controller;

import com.mju.management.domain.post.controller.port.PostReadService;
import com.mju.management.domain.post.controller.response.PostResponse;
import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.global.service.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "게시글 읽기")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PostReadController {

    private final PostReadService postReadService;
    private final ResponseService responseService;

    // 전체 조희 + 페이징
    @Operation(summary = "기획/제작/편집 게시글 전체 조회 API (category : PLANNING, PRODUCTION, EDITING)")
    @GetMapping("/all")
    public CommonResult readPosts(@RequestParam("category") String category
            /* @AuthenticationPrincipal User user */){
        long userId = 1L;
        List<PostResponse> responseList = postReadService.readPosts(userId, category);
        return responseService.getListResult(responseList);
    }

    // 검색 + 페이징

}
