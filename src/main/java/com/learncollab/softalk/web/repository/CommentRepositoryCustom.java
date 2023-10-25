package com.learncollab.softalk.web.repository;

public interface CommentRepositoryCustom {

    Long countByParentCommentId(Long commentId);

}
