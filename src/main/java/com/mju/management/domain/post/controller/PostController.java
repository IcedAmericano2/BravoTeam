package com.mju.management.domain.post.controller;

import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.domain.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "기획, 제작, 편집 (posts)")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @Operation(summary = "기획 or 제작 or 편집 생성")
    @PostMapping
    public CommonResult createPost(){
        return postService.create();
    }

    @Operation(summary = "기획 or 제작 or 편집 하나 읽기")
    @GetMapping("/{post_id}")
    public CommonResult readPost(@PathVariable Long post_id){
        return postService.readPost(post_id);
    }

    @Operation(summary = "기획 or 제작 or 편집 전체 읽기")
    @GetMapping
    public CommonResult readPosts(){
        return postService.readPosts();
    }

    @Operation(summary = "기획 or 제작 or 편집 수정")
    @PutMapping("/{post_id}")
    public CommonResult updatePost(@PathVariable Long post_id){
        return postService.updatePost(post_id);
    }

    @Operation(summary = "기획 or 제작 or 편집 삭제")
    @PutMapping("/{post_id}")
    public CommonResult deletePost(@PathVariable Long post_id){
        return postService.deletePost(post_id);
    }

    // 댓글

}
