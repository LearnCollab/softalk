package com.learncollab.softalk.web.controller;

import com.learncollab.softalk.domain.dto.member.EmailVerificationReqDto;
import com.learncollab.softalk.domain.dto.member.JoinDto;
import com.learncollab.softalk.domain.dto.member.JwtToken;
import com.learncollab.softalk.exception.member.MemberException;
import com.learncollab.softalk.exception.validator.MemberValidator;
import com.learncollab.softalk.web.security.JwtResponseBuilder;
import com.learncollab.softalk.web.service.AuthenticationService;
import com.learncollab.softalk.web.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

import static com.learncollab.softalk.exception.ExceptionType.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final AuthenticationService authenticationService;
    private final JwtResponseBuilder jwtResponseBuilder;

    /*회원 가입 API*/
    @PostMapping("/join")
    public void join(@RequestBody JoinDto joinDto) {

        String email = joinDto.getEmail();
        String password = joinDto.getPassword();
        String name = joinDto.getName();

        /*예외 체크*/
        emailCheck(email);
        passwordCheck(password);
        nameCheck(name);

        /*예외 처리가 끝나면 회원 저장*/
        memberService.save(joinDto);
    }


    /*일반 로그인 처리 로직*/
    @PostMapping("/login")
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        JwtToken jwtToken = authenticationService.authenticateAndGenerateToken(email, password);

        jwtResponseBuilder.buildJwtResponse(jwtToken, response);
    }

    /*이메일 체크 메서드*/
    private void emailCheck(String email) {
        //예외 코드 10011: 이메일이 비어있을 경우
        if (email.strip().equals("")) {
            throw new MemberException(EMAIL_EMPTY, EMAIL_EMPTY.getCode(), EMAIL_EMPTY.getErrorMessage());
        }

        //예외 코드 10012: 이미 존재하는 회원일 경우
        if (memberService.findMemberByEmail(email).isPresent()) {
            throw new MemberException(EMAIL_ALREADY_EXIST, EMAIL_ALREADY_EXIST.getCode(), EMAIL_ALREADY_EXIST.getErrorMessage());
        }

        //예외 코드 10013: 이메일 형식에 맞지 않을 경우
        if (!MemberValidator.isEmailValid(email)) {
            throw new MemberException(EMAIL_INVALID, EMAIL_INVALID.getCode(), EMAIL_INVALID.getErrorMessage());
        }
    }


    /*비밀번호 체크 메서드*/
    private void passwordCheck(String password) {
        //예외 코드 10014: 비밀번호가 비어있을 경우
        if (password.strip().equals("")) {
            throw new MemberException(PASSWORD_EMPTY, PASSWORD_EMPTY.getCode(), PASSWORD_EMPTY.getErrorMessage());
        }

        //예외 코드 10015: 비밀번호가 형식에 맞지 않은 경우
        if (!MemberValidator.isPasswordValid(password)) {
            throw new MemberException(PASSWORD_INVALID, PASSWORD_INVALID.getCode(), PASSWORD_INVALID.getErrorMessage());
        }
    }


    /*닉네임 체크 메서드*/
    private void nameCheck(String name) {
        //예외 코드 10016: 이름이 비어있을 경우
        if (name.strip().equals("")) {
            throw new MemberException(NAME_EMPTY, NAME_EMPTY.getCode(), NAME_EMPTY.getErrorMessage());
        }

        //예외 코드 10017: 이미 존재하는 이름일 경우
        if (memberService.findMemberByName(name).isPresent()) {
            throw new MemberException(NAME_ALREADY_EXIST, NAME_ALREADY_EXIST.getCode(), NAME_ALREADY_EXIST.getErrorMessage());
        }
    }


    /*이메일 인증번호 전송 API*/
    @PostMapping("/email/code-request")
    public ResponseEntity<String> requestVerificationCode(
            @RequestBody EmailVerificationReqDto.sendCodeRequest request) {
        // TODO memberService.sendCodeToEmail(request);
        return ResponseEntity.ok("인증번호 발송 성공");
    }

}
