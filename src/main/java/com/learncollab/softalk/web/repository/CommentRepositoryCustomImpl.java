package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Comment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;

import static com.learncollab.softalk.domain.entity.QComment.comment;
import static com.learncollab.softalk.domain.entity.QMember.member;
import static com.learncollab.softalk.domain.entity.QPost.post;

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


    /**
     * 게시글 상세 조회 시, 댓글 목록 조회
     */
    @Override
    public List<Comment> findParentCommentList(Long postId) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.writer, member).fetchJoin()
                .join(comment.post, post)
                .where(
                        comment.post.id.eq(postId),
                        comment.parentComment.isNull()
                )
                .orderBy(comment.createdAt.asc())
                .fetch();
    }

    @Override
    public List<Comment> findChildrenCommentList(Long postId, Long parentCommentId) {
        return queryFactory
                .selectFrom(comment)
                .join(comment.writer, member).fetchJoin()
                .join(comment.post, post)
                .join(comment.parentComment)
                .where(
                        post.id.eq(postId),
                        comment.parentComment.id.eq(parentCommentId)
                )
                .orderBy(comment.createdAt.asc())
                .fetch();
    }

    /**
     * 전체 댓글 개수 카운트
        - 삭제된 댓글 제외
     */
    @Override
    public Long countByPostId(Long postId) {
        return queryFactory
                .selectFrom(comment)
                .where(
                        comment.post.id.eq(postId),
                        comment.content.ne("삭제된 댓글입니다."))
                .fetchCount();
    }


}
