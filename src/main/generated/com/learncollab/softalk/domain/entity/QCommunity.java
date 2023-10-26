package com.learncollab.softalk.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCommunity is a Querydsl query type for Community
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCommunity extends EntityPathBase<Community> {

    private static final long serialVersionUID = -454352090L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCommunity community = new QCommunity("community");

    public final QBaseTime _super = new QBaseTime(this);

    public final NumberPath<Integer> category = createNumber("category", Integer.class);

    public final ListPath<CommunityMember, QCommunityMember> cm_members = this.<CommunityMember, QCommunityMember>createList("cm_members", CommunityMember.class, QCommunityMember.class, PathInits.DIRECT2);

    public final StringPath cm_name = createString("cm_name");

    public final NumberPath<Integer> cm_type = createNumber("cm_type", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<CmImage, QCmImage> image = this.<CmImage, QCmImage>createList("image", CmImage.class, QCmImage.class, PathInits.DIRECT2);

    public final QMember manager;

    public final NumberPath<Integer> members_limit = createNumber("members_limit", Integer.class);

    public final NumberPath<Integer> members_number = createNumber("members_number", Integer.class);

    public final NumberPath<Integer> state = createNumber("state", Integer.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QCommunity(String variable) {
        this(Community.class, forVariable(variable), INITS);
    }

    public QCommunity(Path<? extends Community> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCommunity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCommunity(PathMetadata metadata, PathInits inits) {
        this(Community.class, metadata, inits);
    }

    public QCommunity(Class<? extends Community> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.manager = inits.isInitialized("manager") ? new QMember(forProperty("manager")) : null;
    }

}

