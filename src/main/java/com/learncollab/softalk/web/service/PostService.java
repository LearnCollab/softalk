package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.post.PostReqDto;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.entity.Post;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.web.repository.CommunityRepository;
import com.learncollab.softalk.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.learncollab.softalk.exception.ExceptionType.NO_SUCH_Community;

/**
 * 커뮤니티 내 게시글 CRUD
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberService memberService;
    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;

    // 게시글 등록
    public void createPost(Long communityId, PostReqDto request) {
        //사용자 인증
        Member writer = memberService.findLoginMember();

        //커뮤니티
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));

        //게시글 등록
        Post post = request.toEntity(writer, community);
        postRepository.save(post);
    }

}
