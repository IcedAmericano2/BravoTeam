package com.mju.management.domain.post.service;

import com.mju.management.domain.post.controller.port.PostReadService;
import com.mju.management.domain.post.controller.response.PostResponse;
import com.mju.management.domain.post.domain.Post;
import com.mju.management.domain.post.infrastructure.Category;
import com.mju.management.domain.post.infrastructure.PostRepository;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostReadServiceImpl implements PostReadService {

    private final PostRepository postRepository;

    private final ProjectRepository projectRepository;

    @Override
    public List<PostResponse> readPosts(long userId, String category) {
        /**유저 추가해야 함*/

        Category getCategory = null;
        switch (category) {
            case "planning":
                getCategory = Category.PLANNING;
                break;
            case "production":
                getCategory = Category.PRODUCTION;
                break;
            case "editing":
                getCategory = Category.EDITING;
                break;
            default:
                /** 예외 처리 */
        }

        List<Post> postList = postRepository.findByCategory(getCategory);
        List<PostResponse> postResponseList = new ArrayList<>();
        postList.forEach(post->{
            postResponseList.add(PostResponse.from(post));
        });
        return postResponseList;
    }

}
