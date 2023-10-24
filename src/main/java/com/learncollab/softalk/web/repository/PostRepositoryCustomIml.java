package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Post;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;

import java.util.List;

import static com.learncollab.softalk.domain.entity.QPost.post;

public class PostRepositoryCustomIml implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomIml(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 게시글 목록 조회
     */
    @Override
    public Page<Post> getPostList(Pageable pageable, Long communityId, Integer sortBy) {
        QueryResults<Post> results = queryFactory
                .selectFrom(post)
                .join(post.writer).fetchJoin()
                .where(post.community.id.eq(communityId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetchResults();

        List<Post> content = results.getResults();
        long totalCount = results.getTotal();

        return new PageImpl<>(content, pageable, totalCount);
    }
}
