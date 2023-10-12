package com.mju.management.domain.post.service;

import com.mju.management.domain.post.controller.port.PostReadService;
import com.mju.management.domain.post.controller.response.PostResponse;
import com.mju.management.domain.post.domain.Post;
import com.mju.management.domain.post.infrastructure.Category;
import com.mju.management.domain.post.infrastructure.PostRepository;
import com.mju.management.domain.project.infrastructure.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostReadServiceImpl implements PostReadService {

    private final PostRepository postRepository;

    @Override
    public List<PostResponse> readAll(long userId, String category) {
        /**유저 추가해야 함*/
        Category getCategory = getCategory(category);
        List<Post> postList = postRepository.findByCategory(getCategory);
        List<PostResponse> postResponseList = new ArrayList<>();
        postList.forEach(post->{
            postResponseList.add(PostResponse.from(post));
        });
        return postResponseList;
    }

    @Override
    public List<PostResponse> readThree(long userId, String category) {
        /**유저 추가해야 함*/

        Category getCategory = getCategory(category);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt"); // "createdAt" 필드를 기준으로 내림차순 정렬
        Pageable pageable = PageRequest.of(0, 3, sort); // 페이지 번호 0부터 3개의 결과를 가져옴
        List<Post> postList =  postRepository.findByCategory(getCategory, pageable);
        List<PostResponse> postResponseList = new ArrayList<>();
        postList.forEach(post->{
            postResponseList.add(PostResponse.from(post));
        });
        return postResponseList;
    }

    private Category getCategory(String category) {
        Category getCategory = null;
        switch (category) {
            case "PLANNING":
                getCategory = Category.PLANNING;
                break;
            case "PRODUCTION":
                getCategory = Category.PRODUCTION;
                break;
            case "EDITING":
                getCategory = Category.EDITING;
                break;
            default:
                /** 예외 처리 */
        }
        return getCategory;
    }

}
