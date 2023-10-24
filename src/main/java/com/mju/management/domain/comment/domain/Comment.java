package com.mju.management.domain.comment.domain;

import com.mju.management.domain.post.domain.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Comment {
    private final Long id;
    private final LocalDateTime createdAt;
    private final String content;
    private final Post post;
    private final LocalDateTime updatedAt;

    @Builder
    public Comment(Long id, LocalDateTime createdAt, String content, Post post, LocalDateTime updatedAt) {
        this.id = id;
        this.createdAt = createdAt;
        this.content = content;
        this.post = post;
        this.updatedAt = updatedAt;
    }

    public static Comment from(Post post, CommentCreate commentCreate) {
        return Comment.builder()
                .content(commentCreate.getContent())
                .createdAt(LocalDateTime.now())
                .updatedAt(null)
                .post(post)
                .build();
    }

    public Comment update(CommentUpdate commentUpdate) {
        return Comment.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(LocalDateTime.now())
                .content(commentUpdate.getContent())
                .post(post)
                .build();
    }

}
