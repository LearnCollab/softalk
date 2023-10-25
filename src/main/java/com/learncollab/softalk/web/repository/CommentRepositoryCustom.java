package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Comment;

import java.util.List;

public interface CommentRepositoryCustom {

    Long countByParentCommentId(Long commentId);

    List<Comment> getParentCommentList(Long postId);

    List<Comment> getChildrenCommentList(Long postId, Long parentCommentId);

}
