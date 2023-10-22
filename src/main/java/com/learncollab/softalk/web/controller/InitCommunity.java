package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.member.JoinDto;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.CommunityMember;
import com.learncollab.softalk.domain.entity.Member;
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

            JoinDto joinDto = new JoinDto("manager@naver.com", "123456", "매니저");
            memberService.save(joinDto);
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

        }
    }

}
