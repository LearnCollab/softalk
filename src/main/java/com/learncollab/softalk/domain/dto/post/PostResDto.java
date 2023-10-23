package com.learncollab.softalk.domain.dto.post;

import lombok.Getter;

import java.util.List;

public class PostResDto {

    @Getter
    public static class PostList {
        private Long totalCount;
        private Integer pageNum;
        private Boolean hasNext;
        private Boolean hasPrevious;
        private List<PostListDetail> data;
    }

    @Getter
    public static class PostListDetail {
        private Long postId;
        private String postDate;
        private String name;
        private String title;
        private String content;
    }
}
