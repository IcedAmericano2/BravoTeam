package com.mju.management.domain.comment.infrastructure;

import com.mju.management.domain.comment.domain.Comment;
import com.mju.management.domain.post.domain.Post;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comment")
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_index")
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String content;

    @ManyToOne
    @JoinColumn(name = "post_index")
    private Post post;

    public static CommentEntity from(Comment comment) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.id = comment.getId();
        commentEntity.content = comment.getContent();
        commentEntity.createdAt = comment.getCreatedAt();
        commentEntity.post = comment.getPost();
        commentEntity.updatedAt = comment.getUpdatedAt();
        return commentEntity;
    }

    public Comment toModel(){
        return Comment.builder()
                .id(id)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .content(content)
                .post(post)
                .build();
    }

    /** 유저 추가 */
}
