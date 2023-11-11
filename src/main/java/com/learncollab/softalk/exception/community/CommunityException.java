package com.learncollab.softalk.exception.community;

import com.learncollab.softalk.exception.ExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommunityException extends RuntimeException {

    private ExceptionType exceptionType;
    private int code;
    private String errorMessage;
    @Override
    public String getMessage() {
        return errorMessage;
    }

}
