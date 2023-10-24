package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.member.JoinDto;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.CommunityMember;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.entity.Post;
import com.learncollab.softalk.web.service.MemberService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 커뮤니티 내 게시글 CRUD 테스트를 위한 커뮤니티 샘플 데이터
 */
@Component
@RequiredArgsConstructor
public class InitCommunity {

    private final InitCommunityService initCommunityService;

    @PostConstruct
    public void init(){
        initCommunityService.init();
    }

    @Component
    @RequiredArgsConstructor
    static class InitCommunityService {

        @PersistenceContext
        private EntityManager em;

        private final MemberService memberService;

        @Transactional
        public void init(){

            // 커뮤니티 샘플 데이터 생성
            JoinDto joinManagerDto = new JoinDto("manager@naver.com", "123456", "매니저");
            memberService.save(joinManagerDto);
            Member manager = memberService.findMemberByEmail("manager@naver.com").get();

            Community community = Community.builder()
                    .cm_name("샘플 커뮤니티")
                    .cm_type(0)
                    .members_limit(10)
                    .manager(manager)
                    .build();
            em.persist(community);

            CommunityMember communityMember = CommunityMember.builder()
                    .member(manager)
                    .community(community)
                    .build();
            em.persist(communityMember);

            // 게시글 샘플 데이터 생성
            JoinDto joinMemberDto1 = new JoinDto("member1@naver.com", "123456", "멤버1");
            JoinDto joinMemberDto2 = new JoinDto("member2@naver.com", "123456", "멤버2");
            memberService.save(joinMemberDto1);
            memberService.save(joinMemberDto2);
            Member m1 = memberService.findMemberByEmail("member1@naver.com").get();
            Member m2 = memberService.findMemberByEmail("member2@naver.com").get();
            for(int i=0; i<10; i++){
                em.persist(Post.builder()
                                .community(community)
                                .title("게시글 "+(i+1))
                                .content("테스트 내용")
                                .writer(i % 2 == 0 ? m1 : m2)
                                .build());
            }

        }
    }

}
