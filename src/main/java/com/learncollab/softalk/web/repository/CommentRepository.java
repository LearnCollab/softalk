package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {

    @Modifying
    @Query("DELETE FROM Comment c " +
            "where c.post.id = :postId and c.parentComment is not null")
    void deleteChildrenByPostId(@Param("postId") Long postId);

    void deleteByPostId(Long postId);

}
