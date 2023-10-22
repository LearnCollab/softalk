package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Community;
import com.querydsl.core.BooleanBuilder;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.learncollab.softalk.domain.entity.QCommunity.community;

@Repository
public class CommunityRepositoryImpl implements CommunityRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Autowired
    public CommunityRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Community> searchCommunity(Integer state, Integer category, String cm_name) {
        return null;
    }

    @Override
    public List<Community> communityMainList(Integer state, Integer category) {
        BooleanBuilder builder = new BooleanBuilder();

        if (state != null) {
            builder.and(community.state.eq(state));
        }

        if (category != null) {
            builder.and(community.category.eq(category));
        }

        return queryFactory.selectFrom(community)
                .where(builder)
                .orderBy(community.createdAt.desc())
                .fetch();
    }
}
