package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.member.PostReqDto;
import com.learncollab.softalk.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public void createPost(@RequestBody PostReqDto request){
        postService.createPost(request);
    }


}
