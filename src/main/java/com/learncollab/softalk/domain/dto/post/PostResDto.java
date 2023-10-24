package com.learncollab.softalk.domain.dto.post;

import com.learncollab.softalk.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostResDto {

    @Getter
    @Builder
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
        private String writerName;
        private String title;
        private String content;

        public PostListDetail(Post post){
            // TODO 날짜 반환 형식 수정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");

            this.postId = post.getId();
            this.postDate = post.getCreatedAt().format(formatter); //작성일
            this.writerName = post.getWriter().getName();
            this.title = post.getTitle();
            this.content = post.getContent();
        }
    }
}
