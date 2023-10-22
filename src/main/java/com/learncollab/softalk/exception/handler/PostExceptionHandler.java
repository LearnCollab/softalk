package com.learncollab.softalk.exception.handler;

import com.learncollab.softalk.exception.ErrorResult;
import com.learncollab.softalk.exception.post.PostException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class PostExceptionHandler {

    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorResult> CommunityExceptionHandle(PostException e, HttpServletRequest request) {
        log.error("[CustomException] url: {} | errorType: {} | errorMessage: {} | cause Exception: {}",
                request.getRequestURL(), e.getExceptionType(), e.getMessage(), e.getCause());
        ErrorResult errorResult = new ErrorResult(String.valueOf(e.getCode()), e.getErrorMessage());
        return new ResponseEntity<>(errorResult, e.getExceptionType().getHttpStatus());
    }

}

