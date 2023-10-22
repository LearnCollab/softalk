package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.community.CommunityDto;
import com.learncollab.softalk.domain.dto.community.CommunityListResDto;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.web.repository.CommunityRepository;
import com.learncollab.softalk.web.repository.CommunityRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.learncollab.softalk.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityRepositoryImpl communityRepositoryImpl;
    private final MemberService memberService;


    /*커뮤니티 리스트*/
    @Transactional
    public List<CommunityListResDto> communityList(Integer state, Integer category){

        List<Community> communityList = communityRepositoryImpl.communityMainList(state, category);

        return convertToCommunityListResDto(communityList);
    }


    /*community 객체를 CommunityListResDto 객체로 변환*/
    @Transactional
    public List<CommunityListResDto> convertToCommunityListResDto(List<Community> communityList) {
        return communityList.stream()
                .map(community -> new CommunityListResDto(
                        community.getCm_name(),
                        community.getCm_type(),
                        community.getMembers_limit(),
                        community.getMembers_number(),
                        community.getManager().getName(),
                        community.getState(),
                        community.getCategory()
                ))
                .collect(Collectors.toList());
    }

    /*새로운 커뮤니티 생성*/
    public void create(Authentication authentication, CommunityDto communityDto){
        CommunityDto communityDto1 = setCommunityDto(authentication, communityDto);
        Community community = communityRepository.save(new Community(communityDto1));
    }

    /*(새로운 커뮤니티 생성) 들어온 값에 따라 CommunityDto 값 설정*/
    private CommunityDto setCommunityDto(Authentication authentication, CommunityDto communityDto) {
        Member manager = memberService.findLoginMember(authentication);
        communityDto.setManager(manager);
        communityDto.setMembers_number(1);
        communityDto.setState(0);
        return communityDto;
    }

    /*커뮤니티 삭제*/
    public void deleteCommunity(Long communityId, Authentication authentication) {
        Member member = memberService.findLoginMember(authentication);
        Community community = findCommunity(communityId);

        checkCommunityPermisstion(member, community);
        communityRepository.delete(community);
    }

    /*커뮤니티 확인, 반환*/
    public Community findCommunity(Long communityId){
        return communityRepository.findById(communityId)
                .orElseThrow(() -> new CommunityException(NO_SUCH_Community, NO_SUCH_Community.getCode(), NO_SUCH_Community.getErrorMessage()));
    }

    /*로그인 유저가 커뮤니티 매니저인지 확인*/
    public void checkCommunityPermisstion(Member member, Community community){
        if(!community.getManager().getId().equals(member.getId())){
            throw new CommunityException(PERMISSION_DENIED, PERMISSION_DENIED.getCode(), PERMISSION_DENIED.getErrorMessage());
        }
    }



}

