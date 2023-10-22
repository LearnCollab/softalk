package com.learncollab.softalk.domain.dto.post;

import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostReqDto {

    private String title;
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
