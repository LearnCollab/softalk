package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.member.JwtToken;
import com.learncollab.softalk.web.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public JwtToken authenticateAndGenerateToken(String email, String password) {
        // 1. 사용자 인증
        Authentication authentication = createBasicAuthentication(email, password);
        // 2. JWT 토큰 생성
        JwtToken token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    /*일반 로그인 Authentication 객체 생성*/
    public Authentication createBasicAuthentication(String email, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return authentication;
    }
}


