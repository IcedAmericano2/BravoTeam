package com.mju.management.domain.comment.service.port;

import com.mju.management.domain.comment.domain.Comment;

import java.util.Optional;

public interface CommentRepository {

    Optional<Comment> findById(Long commentId);

    Comment save(Comment comment);

    void delete(Comment comment);

}
