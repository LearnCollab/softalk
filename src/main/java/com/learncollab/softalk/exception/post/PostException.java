package com.learncollab.softalk.exception.post;

import com.learncollab.softalk.exception.ExceptionType;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {

    private ExceptionType exceptionType;
    private int code;
    private String errorMessage;

    public PostException(ExceptionType e){
        exceptionType = e;
        code = exceptionType.getCode();
        errorMessage = e.getErrorMessage();
    }

    public PostException(ExceptionType e, String message){
        exceptionType = e;
        code = e.getCode();
        errorMessage = message;
    }

}
