package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.community.CommunityListResDto;
import com.learncollab.softalk.domain.entity.Community;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.web.repository.CommunityRepository;
import com.learncollab.softalk.web.repository.CommunityRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommunityService {
    private final CommunityRepository communityRepository;
    private final CommunityRepositoryImpl communityRepositoryImpl;


    /*커뮤니티 리스트*/
    public List<CommunityListResDto> communityList(Integer state, Integer category){

        List<Community> communityList = communityRepositoryImpl.communityMainList(state, category);

        return convertToCommunityListResDto(communityList);
    }


    /*community 객체를 CommunityListResDto 객체로 변환*/
    public List<CommunityListResDto> convertToCommunityListResDto(List<Community> communityList) {
        return communityList.stream()
                .map(community -> new CommunityListResDto(
                        community.getCm_name(),
                        community.getCm_type(),
                        community.getMembers_limit(),
                        community.getMembers_number(),
                        community.getManager(),
                        community.getState(),
                        community.getCategory()
                ))
                .collect(Collectors.toList());
    }
}

