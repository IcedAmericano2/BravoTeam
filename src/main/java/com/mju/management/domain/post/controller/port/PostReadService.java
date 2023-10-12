package com.mju.management.domain.post.controller.port;

import com.mju.management.domain.post.controller.response.PostResponse;

import java.util.List;

public interface PostReadService {

    List<PostResponse> readAll(long userId, String category);

    List<PostResponse> readThree(long userId, String category);

}
