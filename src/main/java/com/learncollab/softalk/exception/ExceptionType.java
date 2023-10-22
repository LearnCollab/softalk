package com.learncollab.softalk.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

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
    CM_MEMBER_RANGE_ERR(BAD_REQUEST, 20005, "커뮤니티 멤버 수 범위에 해당되지 않는 요청값입니다."),
    NO_SUCH_Community(BAD_REQUEST, 20006, "존재하지 않는 커뮤니티입니다."),
    PERMISSION_DENIED(BAD_REQUEST, 20007, "해당 커뮤니티에 대한 권한이 없습니다."),

    /*파일 관련 예외*/
    FILE_UPLOAD_FAILED(INTERNAL_SERVER_ERROR, 30001, "파일 업로드 중 오류가 발생하였습니다."),
    FILE_DELETE_FAILED(INTERNAL_SERVER_ERROR, 30002, "이미지 삭제 중 오류가 발생하였습니다."),
    S3_ERROR(INTERNAL_SERVER_ERROR, 30003, "S3 서비스와 통신 중 오류가 발생했습니다.");



    private final HttpStatus httpStatus;
    private final int code;
    private final String errorMessage;

}
