package com.learncollab.softalk.domain.dto.comment;

import com.learncollab.softalk.domain.entity.Comment;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentResDto {

    @Getter
    public static class CommentList {
        private Long commentId;
        private Long parentCommentId;
        private String writerName;
        private String content;
        private String createdAt;
        private String updatedAt;
        private List<CommentReply> children = new ArrayList<>();

        public CommentList(Comment comment, List<CommentReply> children){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");

            this.commentId = comment.getId();
            this.parentCommentId = comment.getParentComment().getId();
            this.writerName = comment.getWriter().getName();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt().format(formatter);
            this.updatedAt = comment.getUpdatedAt().format(formatter);
            this.children = children;
        }

    }

    @Getter
    public static class CommentReply {
        private Long commentId;
        private Long parentCommentId;
        private String writerName;
        private String content;
        private String createdAt;
        private String updatedAt;

        public CommentReply(Comment comment){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");

            this.commentId = comment.getId();
            this.parentCommentId = comment.getParentComment().getId();
            this.writerName = comment.getWriter().getName();
            this.content = comment.getContent();
            this.createdAt = comment.getCreatedAt().format(formatter);
            this.updatedAt = comment.getUpdatedAt().format(formatter);
        }
    }



}
