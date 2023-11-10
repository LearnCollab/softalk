package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.member.JwtToken;
import com.learncollab.softalk.exception.member.MemberException;
import com.learncollab.softalk.web.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import static com.learncollab.softalk.exception.ExceptionType.NO_SUCH_MEMBER;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtToken authenticateAndGenerateToken(String email, String password) {
        try {
            // 1. 사용자 인증
            Authentication authentication = createBasicAuthentication(email, password);
            // 2. JWT 토큰 생성
            JwtToken token = jwtTokenProvider.generateToken(authentication);
            return token;
        } catch (AuthenticationException e) {
            // 로그인 실패 시 처리
            throw new MemberException(NO_SUCH_MEMBER, NO_SUCH_MEMBER.getCode(), NO_SUCH_MEMBER.getErrorMessage());
        }
    }

    /*일반 로그인 Authentication 객체 생성*/
    public Authentication createBasicAuthentication(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return authentication;
    }
}


