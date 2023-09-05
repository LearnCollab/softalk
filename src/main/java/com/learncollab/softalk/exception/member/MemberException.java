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
}
