package com.mju.management.domain.post.domain;

import java.time.LocalDateTime;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mju.management.domain.post.infrastructure.Category;
import com.mju.management.domain.post.model.dto.request.UpdatePostRequestServiceDto;
import com.mju.management.domain.project.infrastructure.Project;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreatedDate
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	private String title;

	private String content;

	@Enumerated(EnumType.STRING)
	private Category category;

	private int reply_cnt = 0;

	// TODO : 게시글 상세 조회 API에 작성자 이름 표시, 게시글 수정 및 삭제 권한 확인
	// @ManyToOne
	// @JoinColumn("user_id")
	// private User writer;


	// TODO: 프로젝트의 팀원일때만, 게시글을 작성할 수 있도록 확인
	@ManyToOne
	@JoinColumn(name = "project_index")
	@JsonIgnore
	private Project project;

	// TODO : User 기능 추가되면 User writer 필드 추가
	@Builder
	public Post(String title, String content, Category category) {
		this.title = title;
		this.content = content;
		this.category = category;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void update(UpdatePostRequestServiceDto dto) {
		this.title = dto.title();
		this.content = dto.content();
		this.category = dto.category();
	}
}
