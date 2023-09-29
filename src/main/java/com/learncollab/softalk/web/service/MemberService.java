package com.learncollab.softalk.web.service;

import com.learncollab.softalk.domain.dto.member.EmailVerificationReqDto;
import com.learncollab.softalk.domain.dto.member.JoinDto;
import com.learncollab.softalk.domain.dto.member.JwtToken;
import com.learncollab.softalk.domain.entity.Member;
import com.learncollab.softalk.exception.member.MemberException;
import com.learncollab.softalk.web.email.MailService;
import com.learncollab.softalk.web.email.RedisService;
import com.learncollab.softalk.web.repository.MemberRepository;
import com.learncollab.softalk.web.security.JwtTokenProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MailService mailService;
    private final RedisService redisService;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;


    /*이메일 인증번호 전송*/
    public void sendCodeToEmail(EmailVerificationReqDto.sendCodeRequest request) {
        // 이메일 가입 여부 확인
        memberRepository.findByEmail(request.getEmail()).ifPresent(user -> {
            throw new MemberException(EMAIL_ALREADY_EXIST);
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
            throw new MemberException(VERIFICATION_CODE_GENERATION_ERROR);
        }
    }

    /*이메일 인증번호 검증*/
    public void verifyCode(EmailVerificationReqDto.verifyCodeRequest request) {
        String redisAuthCode = redisService.getCode(request.getEmail());
        boolean authResult = redisAuthCode.equals(request.getCode());
        if(authResult == false){
            throw new MemberException(VERIFICATION_CODE_MISMATCH);
        }
    }

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
