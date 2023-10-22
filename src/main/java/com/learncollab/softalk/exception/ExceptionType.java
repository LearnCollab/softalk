package com.learncollab.softalk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@RequiredArgsConstructor
public enum ExceptionType {

    /*회원 관련 예외*/

    //인증 관련 예외
    NO_SUCH_MEMBER(BAD_REQUEST, 10001, "일치하는 회원 정보가 없습니다."),

    //회원 가입 예외
    EMAIL_EMPTY(BAD_REQUEST, 10011, "이메일 주소는 필수 입력값입니다."),
    EMAIL_ALREADY_EXIST(BAD_REQUEST, 10012, "이미 존재하는 이메일입니다."),
    EMAIL_INVALID(BAD_REQUEST, 10013, "이메일 형식이 올바르지 않습니다."),
    PASSWORD_EMPTY(BAD_REQUEST, 10014, "비밀번호는 필수 입력값입니다."),
    PASSWORD_INVALID(BAD_REQUEST, 10015, "7-16자의 영문 대소문자, 숫자, 특수문자 ((!), (_) , (-))를 포합해야합니다."),
    NAME_EMPTY(BAD_REQUEST, 10016, "닉네임은 필수 입력값입니다."),
    NAME_ALREADY_EXIST(BAD_REQUEST, 10017, "이미 존재하는 닉네임입니다."),


    /*커뮤니티 관련 예외*/
    CATEGORY_RANGE_ERR(BAD_REQUEST, 20001, "카테고리 범위에 해당되지 않는 요청값입니다."),
    STATE_RANGE_ERR(BAD_REQUEST, 20002, "상태 범위에 해당되지 않는 요청값입니다."),

    CM_NAME_EMPTY(BAD_REQUEST, 20003, "커뮤니티 제목은 필수 입력값입니다."),
    CM_TYPE_RAGE_ERR(BAD_REQUEST, 20004, "커뮤니티 타입 범위에 해당되지 않는 요청값입니다."),
    CM_MEMBER_RANGE_ERR(BAD_REQUEST, 20005, "커뮤니티 멤버 수 범위에 해당되지 않는 요청값입니다.");


    private final HttpStatus httpStatus;
    private final int code;
    private final String errorMessage;

}
