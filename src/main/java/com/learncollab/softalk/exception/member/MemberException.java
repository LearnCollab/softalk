package com.learncollab.softalk.exception.member;

import com.learncollab.softalk.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberException extends RuntimeException{

    private ExceptionType exceptionType;
    private int code;
    private String errorMessage;

    public MemberException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
        this.code = exceptionType.getCode();
        this.errorMessage = exceptionType.getErrorMessage();
    }
}
