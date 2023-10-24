package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.comment.CommentReqDto;
import com.learncollab.softalk.domain.entity.Comment;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.entity.Post;
import com.learncollab.softalk.exception.comment.CommentException;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.exception.member.MemberException;
import com.learncollab.softalk.exception.post.PostException;
import com.learncollab.softalk.web.repository.CommentRepository;
import com.learncollab.softalk.web.repository.CommunityRepository;
import com.learncollab.softalk.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.learncollab.softalk.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final MemberService memberService;
    private final CommentRepository commentRepository;
    private final CommunityRepository communityRepository;
    private final PostRepository postRepository;


    // 댓글 등록
    public void createComment(CommentReqDto.CommentCreate request) {

        //유저 인증
        Member writer = memberService.findLoginMember();
        if(writer == null){
            throw new MemberException(UNAUTHORIZED_ACCESS, UNAUTHORIZED_ACCESS.getCode(), UNAUTHORIZED_ACCESS.getErrorMessage());
        }

        //커뮤니티, 게시글 존재 확인
        Community community = null;
        Post post = null;
        if(request.getCommunityId() != null){
            community = communityRepository.findById(request.getCommunityId())
                    .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));
        }else if(request.getPostId() != null){
            post = postRepository.findById(request.getPostId())
                    .orElseThrow(() -> new PostException(NO_SUCH_POST));
        }

        //대댓글 작성 시, 해당 부모 댓글이 있는지 확인
        Comment parentComment = null;
        Long parentId = request.getParentCommentId();
        if(parentId != null){
            parentComment = commentRepository.findById(parentId)
                    .orElseThrow(() -> new CommentException(NO_SUCH_COMMENT, "부모 댓글이 존재하지 않습니다."));
        }

        //댓글 등록
        Comment comment = request.toEntity(writer, community, post, parentComment);
        commentRepository.save(comment);
    }


    // 댓글 수정
    public void updateComment(Long commentId, CommentReqDto.CommentUpdate request) {
        // TODO 댓글 수정 로직 구현
    }


}
