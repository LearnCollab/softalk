package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.member.EmailVerificationReqDto;
import com.learncollab.softalk.domain.dto.member.JoinDto;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.domain.event.OAuth2UserRegisteredEvent;
import com.learncollab.softalk.exception.member.MemberException;
import com.learncollab.softalk.web.email.MailService;
import com.learncollab.softalk.web.email.RedisService;
import com.learncollab.softalk.web.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import static com.learncollab.softalk.exception.ExceptionType.*;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService, ApplicationListener<OAuth2UserRegisteredEvent> {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final MailService mailService;
    private final RedisService redisService;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;


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

    /*authentication 객체로 현재 로그인 멤버 찾기*/
    public Optional<Member> findMemberByAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return findMemberByEmail(email);
    }

    /*로그인 유저 확인, 반환*/
    public Member findLoginMember(Authentication authentication){
        String email = authentication.getName();
        return findMemberByEmail(email).orElseThrow(() -> new MemberException(NO_SUCH_MEMBER, NO_SUCH_MEMBER.getCode(), NO_SUCH_MEMBER.getErrorMessage()));
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

    /**
     * 이메일 인증
     */
    /*이메일 인증번호 전송*/
    public void sendCodeToEmail(EmailVerificationReqDto.sendCodeRequest request) {
        // 이메일 가입 여부 확인
        memberRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new MemberException(EMAIL_ALREADY_EXIST, EMAIL_ALREADY_EXIST.getCode(), EMAIL_ALREADY_EXIST.getErrorMessage());
        });

        String authCode = createCode();
        mailService.sendEmail(request.getEmail(), authCode);

        // 인증번호 Redis에 저장 ( key = Email / value = AuthCode )
        redisService.saveCode(request.getEmail(), authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    /*랜덤 인증번호 생성*/
    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new MemberException(VERIFICATION_CODE_GENERATION_ERROR, VERIFICATION_CODE_GENERATION_ERROR.getCode(), VERIFICATION_CODE_GENERATION_ERROR.getErrorMessage());
        }
    }

}
