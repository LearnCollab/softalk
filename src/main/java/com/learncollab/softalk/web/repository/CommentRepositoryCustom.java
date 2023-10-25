package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    Long countByParentCommentId(Long commentId);

    List<Comment> findParentCommentList(Long postId);

    List<Comment> findChildrenCommentList(Long postId, Long parentCommentId);

}
