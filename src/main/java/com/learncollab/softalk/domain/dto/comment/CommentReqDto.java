package com.learncollab.softalk.domain.dto.comment;

import com.learncollab.softalk.domain.entity.Comment;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReqDto {

    private Long communityId;
    private Long postId;

    private Long parentCommentId;

    @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
    private String content;

    public Comment toEntity(Member writer, Community community, Post post, Comment parentComment){
        return Comment.builder()
                .writer(writer)
                .community(community)
                .post(post)
                .parentComment(parentComment)
                .content(content)
                .build();
    }

}
