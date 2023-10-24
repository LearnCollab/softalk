package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.post.PostReqDto;
import com.learncollab.softalk.domain.dto.post.PostResDto;
import com.learncollab.softalk.web.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/softalk/community")
public class PostController {

    private final PostService postService;

    @GetMapping("/{communityId}")
    public ResponseEntity<PostResDto.PostList> getPostList(
            @PathVariable("communityId") Long communityId,
            @RequestParam(value = "sortBy", defaultValue = "0") int sortBy, //최신순
            Pageable pageable
    ){
        return ResponseEntity.ok().body(postService.getPostList(communityId, sortBy, pageable));
    }

    @PostMapping("/{communityId}")
    public ResponseEntity<Void> createPost(@PathVariable("communityId") Long communityId,
                                           @Valid @RequestBody PostReqDto request){
        postService.createPost(communityId, request);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{communityId}/{postId}")
    public ResponseEntity<Void> updatePost(@Valid @RequestBody PostReqDto request,
                                           @PathVariable("communityId") Long communityId,
                                           @PathVariable("postId") Long postId){
        postService.updatePost(request, communityId, postId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{communityId}/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable("communityId") Long communityId,
                                           @PathVariable("postId") Long postId){
        postService.deletePost(communityId, postId);
        return ResponseEntity.ok().build();
    }


}
