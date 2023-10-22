package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.post.PostReqDto;
import com.learncollab.softalk.web.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/softalk/community")
public class PostController {

    private final PostService postService;

    @PostMapping("/{communityId}")
    public ResponseEntity<Void> createPost(@PathVariable("communityId") Long communityId,
                                           @RequestBody PostReqDto request){
        postService.createPost(communityId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{communityId}/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("communityId") Long communityId,
                                           @PathVariable("postId") Long postId){
        postService.deletePost(communityId, postId);
        return ResponseEntity.ok().build();
    }


}
