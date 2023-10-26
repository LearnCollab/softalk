package com.learncollab.softalk.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunityMember is a Querydsl query type for CommunityMember
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunityMember extends EntityPathBase<CommunityMember> {

    private static final long serialVersionUID = 639182176L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunityMember communityMember = new QCommunityMember("communityMember");

    public final QCommunity community;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QCommunityMember(String variable) {
        this(CommunityMember.class, forVariable(variable), INITS);
    }

    public QCommunityMember(Path<? extends CommunityMember> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunityMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunityMember(PathMetadata metadata, PathInits inits) {
        this(CommunityMember.class, metadata, inits);
    }

    public QCommunityMember(Class<? extends CommunityMember> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.community = inits.isInitialized("community") ? new QCommunity(forProperty("community"), inits.get("community")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

