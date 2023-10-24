package com.learncollab.softalk.domain.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentReqDto {

    private Long communityId;
    private Long postId;

    @NotBlank(message = "댓글 내용은 필수 입력값입니다.")
    private String content;

}
