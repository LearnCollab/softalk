package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
