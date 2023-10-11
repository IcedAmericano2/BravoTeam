package com.mju.management.domain.post.service;

import com.mju.management.domain.post.controller.port.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

}
