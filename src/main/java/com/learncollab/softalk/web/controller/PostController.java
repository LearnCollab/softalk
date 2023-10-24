package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.post.PostReqDto;
import com.learncollab.softalk.domain.dto.post.PostResDto;
import com.learncollab.softalk.exception.ExceptionType;
import com.learncollab.softalk.exception.post.PostException;
import com.learncollab.softalk.web.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.learncollab.softalk.exception.ExceptionType.INVALID_VALUE;


@RestController
@RequiredArgsConstructor
@RequestMapping("/softalk/community")
public class PostController {

    private final PostService postService;

    @GetMapping("/{communityId}")
    public ResponseEntity<PostResDto.PostList> getPostList(
            @PathVariable("communityId") Long communityId,
            @RequestParam(value = "type", required = false, defaultValue = "0") int type, //전체 목록
            @RequestParam(value = "sortBy", required = false, defaultValue = "0") int sortBy, //최신순
            Pageable pageable
    ){
        // query string 값 범위 확인
        if (!(type == 0 || type == 1)){
            throw new PostException(INVALID_VALUE, "type 값은 0(전체 목록) 또는 1(내가 쓴 게시글 목록)만 가능합니다.");
        }
        if (!(sortBy == 0 || sortBy == 1)){
            throw new PostException(INVALID_VALUE, "sortBy 값은 0(최신순) 또는 1(오래된순)만 가능합니다.");
        }

        return ResponseEntity.ok().body(postService.getPostList(communityId, type, sortBy, pageable));
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
