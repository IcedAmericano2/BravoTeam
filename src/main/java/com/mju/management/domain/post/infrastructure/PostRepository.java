package com.mju.management.domain.post.infrastructure;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.mju.management.domain.post.domain.Post;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCategory(Category getCategory);

    List<Post> findByCategory(Category getCategory, Pageable pageable);
}
