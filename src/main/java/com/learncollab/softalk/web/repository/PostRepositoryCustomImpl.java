package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Post;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static com.learncollab.softalk.domain.entity.QPost.post;

public class PostRepositoryCustomImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PostRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    /**
     * 게시글 목록 조회
     */
    @Override
    public Page<Post> getPostList(Pageable pageable,
                                  Long communityId, int type, Long memberId, int sortBy) {
        QueryResults<Post> results = queryFactory
                .selectFrom(post)
                .join(post.writer).fetchJoin()
                .where(
                        post.community.id.eq(communityId),
                        filterType(type, memberId)
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(
                        sortBy(sortBy)
                )
                .fetchResults();

        List<Post> content = results.getResults();
        long totalCount = results.getTotal();

        return new PageImpl<>(content, pageable, totalCount);
    }


    // [type] 0(default): 전체 목록 / 1: 내가 작성한 게시글 목록
    private BooleanExpression filterType(Integer type, Long memberId) {
        return type == 1 ? post.writer.id.eq(memberId) : null;
    }

    // [sort]
    private OrderSpecifier<?> sortBy(int sortBy) {
        switch (sortBy){
            //case 0: //최신순(default)
            //    return post.createdAt.desc();
            case 1: //오래된 순
                return post.createdAt.asc();
            default: //최신순(default)
                return post.createdAt.desc();
            // TODO 정렬 조건 추가 예정
        }
    }
}
