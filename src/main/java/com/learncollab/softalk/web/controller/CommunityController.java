package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.community.CommunityDto;
import com.learncollab.softalk.domain.dto.community.CommunityListResDto;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.web.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;

import static com.learncollab.softalk.exception.ExceptionType.*;

@RestController
@RequestMapping("/softalk/community")
@RequiredArgsConstructor
public class CommunityController {

    private final CommunityService communityService;

    /*커뮤니티 목록 조회 API*/
    @GetMapping
    public List<CommunityListResDto> communityList(
            @RequestParam(required = false) Integer state,
            @RequestParam(required = false) Integer category,
            HttpServletRequest request) {

        checkParams(state, category);

        /*카테고리 선택에 맞는 게시글 리스트*/
        return communityService.communityList(state, category);
    }

    /*커뮤니티 생성 API*/
    @PostMapping
    public void createCommunity(
            @RequestBody CommunityDto communityDto,
            Authentication authentication,
            HttpServletRequest request) throws IOException {

        checkCommunityException(communityDto);

        communityService.create(authentication, communityDto);
    }

    public void checkCommunityException(CommunityDto communityDto) {
        //커뮤니티 이름 null
        if(communityDto.getCm_name() == null){
            throw new CommunityException(CM_NAME_EMPTY, CM_NAME_EMPTY.getCode(), CM_NAME_EMPTY.getErrorMessage());
        }
        //cm_type null 혹은 범위 오류
        if(communityDto.getCm_type() == null || communityDto.getCm_type()<0 || communityDto.getCm_type()>1){
            throw new CommunityException(CM_TYPE_RAGE_ERR, CM_TYPE_RAGE_ERR.getCode(), CM_TYPE_RAGE_ERR.getErrorMessage());
        }
        //TODO
        //FIXME
        // - 범위도 설정해야함
        //members_limit null
        if(communityDto.getMembers_limit() ==null){
            throw new CommunityException(CM_MEMBER_RANGE_ERR, CM_MEMBER_RANGE_ERR.getCode(), CM_MEMBER_RANGE_ERR.getErrorMessage());
        }
        //카테고리 null 혹은 범위 오류
        if(communityDto.getCategory() == null || communityDto.getCategory() < 0 || communityDto.getCategory() > 4){
            throw new CommunityException(CATEGORY_RANGE_ERR, CATEGORY_RANGE_ERR.getCode(), CATEGORY_RANGE_ERR.getErrorMessage());
        }
    }

    /*커뮤니티 삭제 API*/
    @DeleteMapping("/{communityId}")
    public void deleteCommunity(
            @PathVariable Long communityId, Authentication authentication, HttpServletRequest request){

        communityService.deleteCommunity(communityId, authentication);
    }

    /*커뮤니티 검색*/
    @GetMapping("/search")
    public List<CommunityListResDto> search(
            @RequestParam String keyword,
            @RequestParam(required = false) Integer state,
            @RequestParam(required = false) Integer category,
            HttpServletRequest request) {

        checkParams(state, category);

        return communityService.searchPosts(state, category, keyword);
    }

    public void checkParams(Integer state, Integer category) {
        //커뮤니티 상태 범위 오류
        if (!(state == null)) {
            if (state < 0 || state > 1) {
                throw new CommunityException(STATE_RANGE_ERR, STATE_RANGE_ERR.getCode(), STATE_RANGE_ERR.getErrorMessage());
            }
        }

        //카테고리 범위 오류
        if (!(category == null)) {
            if (category < 0 || category > 3) {
                throw new CommunityException(CATEGORY_RANGE_ERR, CATEGORY_RANGE_ERR.getCode(), CATEGORY_RANGE_ERR.getErrorMessage());
            }
        }
    }

}