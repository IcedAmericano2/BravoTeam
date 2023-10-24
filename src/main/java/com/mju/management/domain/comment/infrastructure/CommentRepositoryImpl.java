package com.mju.management.domain.comment.infrastructure;

import com.mju.management.domain.comment.domain.Comment;
import com.mju.management.domain.comment.service.port.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJpaRepository;

    @Override
    public Optional<Comment> findById(Long commentId) {
        return commentJpaRepository.findById(commentId).map(CommentEntity::toModel);
    }

    @Override
    public Comment save(Comment comment) {
        return commentJpaRepository.save(CommentEntity.from(comment)).toModel();
    }

    @Override
    public void delete(Comment comment) {
        commentJpaRepository.delete(CommentEntity.from(comment));
    }
}
