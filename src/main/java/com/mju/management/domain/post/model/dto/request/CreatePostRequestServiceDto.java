package com.mju.management.domain.post.model.dto.request;

import com.mju.management.domain.post.domain.Post;
import com.mju.management.domain.post.infrastructure.Category;

public record CreatePostRequestServiceDto(
	Long projectId,
	String title,
	String content,
	Category category
) {
	public Post toEntity() {
		return Post.builder()
			.title(title)
			.content(content)
			.category(category)
			.build();
	}
}
