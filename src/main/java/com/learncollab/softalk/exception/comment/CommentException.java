package com.learncollab.softalk.exception.comment;

import com.learncollab.softalk.exception.ExceptionType;
import lombok.Getter;

@Getter
public class CommentException extends RuntimeException {

    private ExceptionType exceptionType;
    private int code;
    private String errorMessage;

    public CommentException(ExceptionType e){
        exceptionType = e;
        code = e.getCode();
        errorMessage = e.getErrorMessage();
    }

    public CommentException(ExceptionType e, String message){
        exceptionType = e;
        code = e.getCode();
        errorMessage = message;
    }

}
