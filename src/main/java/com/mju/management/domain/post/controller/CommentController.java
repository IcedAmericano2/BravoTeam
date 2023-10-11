package com.mju.management.domain.post.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "댓글")
@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {

    // 댓글

    // 전체 조희 + 페이징
    // 검색 + 페이징

}
