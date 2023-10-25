package com.learncollab.softalk.web.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import static com.learncollab.softalk.domain.entity.QComment.comment;

public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    /**
     * 대댓글 개수 카운트
     */
    @Override
    public Long countByParentCommentId(Long commentId) {
       return queryFactory
               .selectFrom(comment)
               .where(comment.parentComment.id.eq(commentId))
               .fetchCount();
    }


}
