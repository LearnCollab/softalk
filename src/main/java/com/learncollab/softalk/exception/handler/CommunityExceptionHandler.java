package com.learncollab.softalk.exception.handler;

import com.learncollab.softalk.exception.ErrorResult;
import com.learncollab.softalk.exception.community.CommunityException;
import com.learncollab.softalk.exception.community.FileException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommunityExceptionHandler {
    @ExceptionHandler(CommunityException.class)
    public ResponseEntity<ErrorResult> CommunityExceptionHandle(CommunityException e, HttpServletRequest request) {
        log.error("[CustomException] url: {} | errorType: {} | errorMessage: {} | cause Exception: ",
                request.getRequestURL(), e.getExceptionType(), e.getMessage(), e.getCause());
        ErrorResult errorResult = new ErrorResult(String.valueOf(e.getCode()), e.getErrorMessage());
        return new ResponseEntity<>(errorResult, e.getExceptionType().getHttpStatus());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FileException.class)
    public ErrorResult FileExceptionHandle(FileException e, HttpServletRequest request) {
        log.error("[CustomException] url: {} | errorType: {} | errorMessage: {} | cause Exception: ",
                request.getRequestURL(), e.getExceptionType(), e.getMessage(), e.getCause());
        return new ErrorResult(String.valueOf(e.getCode()), e.getErrorMessage());
    }
}
