package com.learncollab.softalk.domain.dto.post;

import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.entity.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReqDto {

    private String title;

    @NotBlank(message = "게시글의 내용은 필수 입력값입니다.")
    private String content;

    public Post toEntity(Member writer, Community community){
        return Post.builder()
                .writer(writer)
                .community(community)
                .title(title)
                .content(content)
                .build();
    }

}
