package com.learncollab.softalk.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCmImage is a Querydsl query type for CmImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCmImage extends EntityPathBase<CmImage> {

    private static final long serialVersionUID = 114589070L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCmImage cmImage = new QCmImage("cmImage");

    public final QCommunity community;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath s3key = createString("s3key");

    public QCmImage(String variable) {
        this(CmImage.class, forVariable(variable), INITS);
    }

    public QCmImage(Path<? extends CmImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCmImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCmImage(PathMetadata metadata, PathInits inits) {
        this(CmImage.class, metadata, inits);
    }

    public QCmImage(Class<? extends CmImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.community = inits.isInitialized("community") ? new QCommunity(forProperty("community"), inits.get("community")) : null;
    }

}

