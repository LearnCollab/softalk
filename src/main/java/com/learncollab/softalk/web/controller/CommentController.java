package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.comment.CommentReqDto;
import com.learncollab.softalk.domain.dto.post.PostReqDto;
import com.learncollab.softalk.web.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/softalk/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@Valid @RequestBody CommentReqDto request){
        commentService.createComment(request);
        return ResponseEntity.ok().build();
    }

}
