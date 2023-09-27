package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.member.JoinDto;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.event.OAuth2UserRegisteredEvent;
import com.learncollab.softalk.web.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
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
public class MemberService implements UserDetailsService, ApplicationListener<OAuth2UserRegisteredEvent> {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;

    /*회원 가입 - 패스워드 인코딩 후 저장*/
    public void save(JoinDto joinDto) {
        Member member = new Member(joinDto, encoder);
        memberRepository.save(member);
    }

    /*소셜 로그인*/
    //TODO
    // - 일반 로그인에서 name은 중복 불가능한데 카카오 로그인할 때의 name은 중복 가능함
    // - 이메일 문제도 있음..
    public void registerIfNewUser(String email, String name, String registrationId) {
        if (!findMemberByEmail(email).isPresent()) {
            saveOAuth2User(email, name, registrationId);
        }
    }

    public void saveOAuth2User(String email, String name, String registrationId){
        Member member = new Member(email, name, registrationId);
        memberRepository.save(member);
    }


    /*이메일 주소로 멤버 찾기*/
    public Optional<Member> findMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    /*닉네임으로 멤버 찾기*/
    public Optional<Member> findMemberByName(String name) {
        return memberRepository.findByName(name);
    }


    /*
    * //implements UserDetailsService
    * 스프링 시큐리티가
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

    @Override
    public void onApplicationEvent(OAuth2UserRegisteredEvent event) {
        registerIfNewUser(event.getEmail(), event.getName(), event.getRegistrationId());
    }
}
