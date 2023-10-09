package com.mju.management.domain.post.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mju.management.domain.post.domain.Post;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
