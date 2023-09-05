package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.member.JoinDto;
import com.learncollab.softalk.domain.dto.member.JwtToken;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.web.repository.MemberRepository;
import com.learncollab.softalk.web.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    /*회원 가입 - 패스워드 인코딩 후 저장*/
    public void save(JoinDto joinDto) {
        Member member = new Member(joinDto, encoder);
        memberRepository.save(member);
    }

    /*로그인*/
    public JwtToken login(String email, String password){
        //Authentication 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        //검증된 인증 정보로 JWT 토큰 생성
        JwtToken token = jwtTokenProvider.generateToken(authentication);
        return token;
    }

    /*이메일 주소로 멤버 찾기*/
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    /*닉네임으로 멤버 찾기*/
    public Optional<Member> findMemberByName(String name) {
        return memberRepository.findByName(name);
    }

    /*스프링 시큐리티가
    * 반환된 UserDetails 객체를 사용하여
    * 제공된 패스워드와 저장된 패스워드 일치하는지, 계정 잠겨 있는지 등을 체크
    * */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .authorities(member.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()))
                .build();
    }
    //implements UserDetailsService
}
