package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostRepositoryCustom {

    Page<Post> getPostList(Pageable pageable,
                           Long communityId, String type, Long memberId, int sortBy);

    Post getPost(Long postId);

}
