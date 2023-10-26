package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.comment.CommentResDto;
import com.learncollab.softalk.domain.dto.post.PostReqDto;
import com.learncollab.softalk.domain.dto.post.PostResDto;
import com.learncollab.softalk.domain.entity.*;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.exception.member.MemberException;
import com.learncollab.softalk.exception.post.PostException;
import com.learncollab.softalk.web.repository.CommentRepository;
import com.learncollab.softalk.web.repository.CommunityRepository;
import com.learncollab.softalk.web.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final CommentRepository commentRepository;
    private final PostImageService postImageService;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private final String bucketDirName = "post";


    /*게시글 목록 조회*/
    public PostResDto.PostList getPostList(Long communityId, String type, int sortBy, Pageable pageable) {

        //로그인 여부 확인
        Long memberId = null;
        Member member = memberService.findLoginMember();
        if(type.equals("my-posts")){
            if(member == null){
                throw new PostException(MEMBER_NOT_AUTHENTICATED);
            }
            memberId = member.getId();
        }

        //커뮤니티 존재 확인
        communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));

        //게시글 목록 조회
        Page<Post> postPage = postRepository.findPostList(pageable, communityId, type, memberId, sortBy);

        List<PostResDto.PostListDetail> data = postPage.getContent().stream()
                .map(PostResDto.PostListDetail::new)
                .collect(Collectors.toList());
        int pageNum = postPage.getNumber();
        long totalCount = postPage.getTotalElements();
        boolean hasNext = postPage.hasNext();
        boolean hasPrevious = postPage.hasPrevious();

        return PostResDto.PostList.builder()
                .totalCount(totalCount)
                .pageNum(pageNum)
                .hasNext(hasNext)
                .hasPrevious(hasPrevious)
                .data(data)
                .build();
    }

    /*게시글 등록*/
    public void createPost(Long communityId, PostReqDto request, List<MultipartFile> multipartFiles) {

        //유저 인증
        Member writer = memberService.findLoginMember();
        if(writer == null){
            throw new MemberException(UNAUTHORIZED_ACCESS, UNAUTHORIZED_ACCESS.getCode(), UNAUTHORIZED_ACCESS.getErrorMessage());
        }

        //커뮤니티 존재 확인
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));

        //게시글 등록
        Post post = request.toEntity(writer, community);
        postRepository.save(post);

        //이미지 등록
        if(!multipartFiles.isEmpty()) {
            //S3에 저장
            List<PostImage> imageList = postImageService.uploadPostImage(multipartFiles, bucketName, bucketDirName);

            //DB에 저장
            if(!imageList.isEmpty()) {
                for(PostImage image : imageList) {
                    postImageService.savePostImage(image, post);
                }
            }
        }
    }


    /*게시글 상세 조회*/
    public PostResDto.PostDetail getPost(Long communityId, Long postId) {

        //커뮤니티&게시글 존재 및 관계 확인
        Community community = communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(NO_SUCH_POST));
        if(community.getId() != post.getCommunity().getId()){
            throw new PostException(COMMUNITY_POST_MISMATCH);
        }

        //게시글 조회
        Post findPost = postRepository.findPost(postId);

        //댓글 목록 조회
        List<Comment> parentComments = commentRepository.findParentCommentList(postId);
        List<CommentResDto.CommentList> commentList = new ArrayList<>();
        for (Comment parent : parentComments) {
            List<Comment> childrenComments = commentRepository.findChildrenCommentList(postId, parent.getId());
            List<CommentResDto.CommentReply> childrenList = childrenComments.stream()
                    .map(CommentResDto.CommentReply::new)
                    .collect(Collectors.toList());

            commentList.add(new CommentResDto.CommentList(parent, childrenList));
        }

        return new PostResDto.PostDetail(findPost, commentList);
    }


    /*게시글 수정*/
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
        if(member == null){
            throw new MemberException(UNAUTHORIZED_ACCESS, UNAUTHORIZED_ACCESS.getCode(), UNAUTHORIZED_ACCESS.getErrorMessage());
        }

        if(post.getWriter().getId() != member.getId()){
            throw new PostException(NO_PERMISSION, "해당 게시글에 대한 수정 권한이 없습니다.");
        }

        //게시글 수정
        post.updatePost(request);

    }

    /*게시글 삭제*/
    @Transactional
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
        if(member == null){
            throw new MemberException(UNAUTHORIZED_ACCESS, UNAUTHORIZED_ACCESS.getCode(), UNAUTHORIZED_ACCESS.getErrorMessage());
        }

        if(post.getWriter().getId() != member.getId()){
            throw new PostException(NO_PERMISSION, "해당 게시글에 대한 삭제 권한이 없습니다.");
        }

        //댓글 삭제
        commentRepository.deleteChildrenByPostId(postId);
        commentRepository.deleteByPostId(postId);

        //이미지 삭제
        List<PostImage> images = post.getImage();
        postImageService.deletePostImage(bucketName, images, postId);

        //게시글 삭제
        postRepository.delete(post);
    }

}
