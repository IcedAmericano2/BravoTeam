package com.mju.management.domain.post.service;

import static com.mju.management.global.model.Exception.ExceptionList.*;

import java.util.Optional;

import com.mju.management.domain.post.controller.response.PostDetailResponse;
import com.mju.management.domain.post.domain.Post;
import com.mju.management.domain.post.infrastructure.PostRepository;
import com.mju.management.domain.post.model.dto.request.CreatePostRequestServiceDto;
import com.mju.management.domain.post.model.dto.request.DeletePostRequestServiceDto;
import com.mju.management.domain.post.model.dto.request.RetrieveDetailPostRequestServiceDto;
import com.mju.management.domain.post.model.dto.request.UpdatePostRequestServiceDto;
import com.mju.management.domain.project.infrastructure.Project;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import com.mju.management.global.model.Result.CommonResult;
import com.mju.management.global.service.ResponseService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl {

    private final PostRepository postRepository;
    private final ProjectRepository projectRepository;

    private final ResponseService responseService;

    // TODO
    // private final UserRepository userRepository;

	public CommonResult createPost(/* User writer, */ CreatePostRequestServiceDto dto) {
        Optional<Project> optionalProject = projectRepository.findById(dto.projectId());
        if (optionalProject.isEmpty()){
            return responseService.getFailResult(INVALID_PROJECT_ID.getCode(), INVALID_PROJECT_ID.getMessage());
        }
        Project project = optionalProject.get();

        // TODO : User 기능이 추가되면 주석 풀 것
        // if(!project.getMembers().contains(writer)){
        //     return responseService.getFailResult(NOT_TEAM_MEMBER.getCode(), NOT_TEAM_MEMBER.getMessage());
        // }

        Post post = dto.toEntity();
        project.createPost(post);
        postRepository.save(post);
        return responseService.getSuccessfulResultWithMessage("기획/제작/편집 게시글 작성에 성공하였습니다.");
    }

    @Transactional(readOnly = true)
    public CommonResult retrieveDetailPost(/* User user, */ RetrieveDetailPostRequestServiceDto dto) {
        Optional<Project> optionalProject = projectRepository.findById(dto.projectId());
        if (optionalProject.isEmpty()){
            return responseService.getFailResult(INVALID_PROJECT_ID.getCode(), INVALID_PROJECT_ID.getMessage());
        }
        Project project = optionalProject.get();

        // TODO : User 기능이 추가되면 주석 풀 것 (사용자가 해당 게시글이 속한 프로젝트의 팀원인지 확인)
        // if(!project.getMembers().contains(user)){
        //     return responseService.getFailResult(NOT_TEAM_MEMBER.getCode(), NOT_TEAM_MEMBER.getMessage());
        // }

        Optional<Post> optionalPost = postRepository.findById(dto.postId());
        if(optionalPost.isEmpty()){
            return responseService.getFailResult(INVALID_POST_ID.getCode(), INVALID_POST_ID.getMessage());
        }

        Post post = optionalPost.get();
        return responseService.getSingleResult(PostDetailResponse.from(post));
    }

    public CommonResult updatePost(UpdatePostRequestServiceDto dto) {
        Optional<Project> optionalProject = projectRepository.findById(dto.projectId());
        if (optionalProject.isEmpty()){
            return responseService.getFailResult(INVALID_PROJECT_ID.getCode(), INVALID_PROJECT_ID.getMessage());
        }
        Project project = optionalProject.get();

        // TODO : User 기능이 추가되면 주석 풀 것 (사용자가 해당 게시글이 속한 프로젝트의 팀원인지 확인)
        // if(!project.getMembers().contains(user)){
        //     return responseService.getFailResult(NOT_TEAM_MEMBER.getCode(), NOT_TEAM_MEMBER.getMessage());
        // }

        Optional<Post> optionalPost = postRepository.findById(dto.postId());
        if(optionalPost.isEmpty()){
            return responseService.getFailResult(INVALID_POST_ID.getCode(), INVALID_POST_ID.getMessage());
        }
        Post post = optionalPost.get();

        // TODO : User 기능이 추가되면 주석 풀 것 (사용자가 해당 게시글의 작성자인지 확인)
        // if(post.getWriter().getId() != user.getId()){
        //     return responseService.getFailResult(NO_PERMISSION_TO_EDIT_POST.getCode(), NO_PERMISSION_TO_EDIT_POST.getMessage());
        // }

        post.update(dto);
        return responseService.getSuccessfulResultWithMessage("기획/제작/편집 게시글 수정에 성공하였습니다.");
    }

    public CommonResult deletePost(DeletePostRequestServiceDto dto) {
        Optional<Project> optionalProject = projectRepository.findById(dto.projectId());
        if (optionalProject.isEmpty()){
            return responseService.getFailResult(INVALID_PROJECT_ID.getCode(), INVALID_PROJECT_ID.getMessage());
        }
        Project project = optionalProject.get();

        // TODO : User 기능이 추가되면 주석 풀 것 (사용자가 해당 게시글이 속한 프로젝트의 팀원인지 확인)
        // if(!project.getMembers().contains(user)){
        //     return responseService.getFailResult(NOT_TEAM_MEMBER.getCode(), NOT_TEAM_MEMBER.getMessage());
        // }

        Optional<Post> optionalPost = postRepository.findById(dto.postId());
        if(optionalPost.isEmpty()){
            return responseService.getFailResult(INVALID_POST_ID.getCode(), INVALID_POST_ID.getMessage());
        }
        Post post = optionalPost.get();

        // TODO : User 기능이 추가되면 주석 풀 것 (사용자가 해당 게시글의 작성자인지 확인)
        // if(post.getWriter().getId() != user.getId()){
        //     return responseService.getFailResult(NO_PERMISSION_TO_EDIT_POST.getCode(), NO_PERMISSION_TO_EDIT_POST.getMessage());
        // }

        postRepository.delete(post);
        return responseService.getSuccessfulResultWithMessage("기획/제작/편집 게시글 삭제에 성공하였습니다.");
    }

}
