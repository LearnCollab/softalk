package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.post.PostReqDto;
import com.learncollab.softalk.domain.dto.post.PostResDto;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.entity.Post;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.exception.post.PostException;
import com.learncollab.softalk.web.repository.CommunityRepository;
import com.learncollab.softalk.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.learncollab.softalk.exception.ExceptionType.*;

/**
 * 커뮤니티 내 게시글 CRUD
 */
@Service
@RequiredArgsConstructor
public class PostService {

    private final MemberService memberService;
    private final PostRepository postRepository;
    private final CommunityRepository communityRepository;

    // 게시글 목록 조회
    public PostResDto.PostList getPostList(Long communityId, int page, int sortBy) {
        // TODO 게시글 목록 조회 서비스 로직 구현
        return null;
    }

    // 게시글 등록
    public void createPost(Long communityId, PostReqDto request) {
        //유저 인증
        Member writer = memberService.findLoginMember();

        //커뮤니티 존재 확인
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));

        //게시글 등록
        Post post = request.toEntity(writer, community);
        postRepository.save(post);
    }

    // 게시글 수정
    @Transactional
    public void updatePost(PostReqDto request, Long communityId, Long postId) {

        //커뮤니티&게시글 존재 및 관계 확인
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(NO_SUCH_POST));
        if(community.getId() != post.getCommunity().getId()){
            throw new PostException(COMMUNITY_POST_MISMATCH);
        }

        //수정 권한 확인
        Member member = memberService.findLoginMember();
        if(post.getWriter().getId() != member.getId()){
            throw new PostException(NO_PERMISSION, "해당 게시글에 대한 수정 권한이 없습니다.");
        }

        //게시글 수정
        post.updatePost(request);

    }

    // 게시글 삭제
    public void deletePost(Long communityId, Long postId) {

        //커뮤니티&게시글 존재 및 관계 확인
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(NO_SUCH_POST));
        if(community.getId() != post.getCommunity().getId()){
            throw new PostException(COMMUNITY_POST_MISMATCH);
        }

        //삭제 권한 확인
        Member member = memberService.findLoginMember();
        if(post.getWriter().getId() != member.getId()){
            throw new PostException(NO_PERMISSION, "해당 게시글에 대한 삭제 권한이 없습니다.");
        }

        //TODO 댓글 삭제

        //게시글 삭제
        postRepository.delete(post);
    }

}
