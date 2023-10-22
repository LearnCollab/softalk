package com.learncollab.softalk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    /*회원 관련 예외*/

    //회원 가입 예외
    EMAIL_EMPTY(BAD_REQUEST, 10011, "이메일 주소는 필수 입력값입니다."),
    EMAIL_ALREADY_EXIST(BAD_REQUEST, 10012, "이미 존재하는 이메일입니다."),
    EMAIL_INVALID(BAD_REQUEST, 10013, "이메일 형식이 올바르지 않습니다."),
    PASSWORD_EMPTY(BAD_REQUEST, 10014, "비밀번호는 필수 입력값입니다."),
    PASSWORD_INVALID(BAD_REQUEST, 10015, "7-16자의 영문 대소문자, 숫자, 특수문자 ((!), (_) , (-))를 포합해야합니다."),
    NAME_EMPTY(BAD_REQUEST, 10016, "닉네임은 필수 입력값입니다."),
    NAME_ALREADY_EXIST(BAD_REQUEST, 10017, "이미 존재하는 닉네임입니다."),
    VERIFICATION_CODE_GENERATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10018, "이메일 인증번호 생성 오류가 발생했습니다."),
    EMAIL_SEND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 10019, "이메일 인증번호 발송 오류가 발생했습니다."),
    VERIFICATION_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, 10020, "이메일 인증번호 검증 오류가 발생했습니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, 10021, "사용자를 찾을 수 없습니다."),
    MEMBER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, 10022, "사용자에게 접근 권한이 없습니다."),

    //커뮤니티
    COMMUNITY_NOT_FOUND(HttpStatus.NOT_FOUND, 20011, "존재하지 않는 커뮤니티입니다.");



    private final HttpStatus httpStatus;
    private final int code;
    private final String errorMessage;

}
