package com.learncollab.softalk.domain.dto.post;

import com.learncollab.softalk.domain.dto.comment.CommentResDto;
import com.learncollab.softalk.domain.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class PostResDto {

    // 게시글 목록조회
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
        private String thumbnailUrl;
        private Long postId;
        private String postDate;
        private String writerName;
        private String title;
        private String content;
        private long commentCount;

        public PostListDetail(Post post, long commentCount, String thumbnailUrl){
            // TODO 날짜 반환 형식 수정
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");

            //this.thumbnailUrl = post.getImages().size() != 0 ? post.getImages().get(0).getImageUrl() : null;
            this.thumbnailUrl = thumbnailUrl;
            this.postId = post.getId();
            this.postDate = post.getCreatedAt().format(formatter); //작성일
            this.writerName = post.getWriter().getName();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.commentCount = commentCount;
        }
    }

    // 게시글 상세조회
    @Getter
    public static class PostDetail {

        private String postCreatedAt;
        private String postUpdatedAt;
        private String writerName;
        private String title;
        private String content;
        private List<String> imageUrlList;
        private List<CommentResDto.CommentList> commentList;
        private long commentCount;
        private boolean isWriter;

        public PostDetail(Post post, List<String> imageUrlList, List<CommentResDto.CommentList> commentList,
                          long commentCount, boolean isWriter){

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");

            this.postCreatedAt = post.getCreatedAt().format(formatter);
            this.postUpdatedAt = post.getUpdatedAt().format(formatter);
            this.writerName = post.getWriter().getName();
            this.title = post.getTitle();
            this.content = post.getContent();
            this.imageUrlList = imageUrlList;
            this.commentList = commentList;
            this.commentCount = commentCount;
            this.isWriter = isWriter;
        }

    }

}
