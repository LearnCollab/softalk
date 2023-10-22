package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.community.CommunityListResDto;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.web.service.CommunityService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

        /*카테고리 선택에 맞는 게시글 리스트*/
        return communityService.communityList(state, category);
    }
}