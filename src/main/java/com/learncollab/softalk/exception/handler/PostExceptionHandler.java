package com.learncollab.softalk.exception.handler;

import com.learncollab.softalk.exception.ErrorResult;
import com.learncollab.softalk.exception.post.PostException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.learncollab.softalk.exception.ExceptionType.INVALID_VALUE;


@RestControllerAdvice
@Slf4j
public class PostExceptionHandler {

    // Post Custom Exception
    @ExceptionHandler(PostException.class)
    public ResponseEntity<ErrorResult> CommunityExceptionHandle(PostException e, HttpServletRequest request) {
        log.error("[CustomException] url: {} | errorType: {} | errorMessage: {} | cause Exception: {}",
                request.getRequestURL(), e.getExceptionType(), e.getMessage(), e.getCause());
        ErrorResult errorResult = new ErrorResult(String.valueOf(e.getCode()), e.getErrorMessage());
        return new ResponseEntity<>(errorResult, e.getExceptionType().getHttpStatus());
    }

    // @Valid Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidException(MethodArgumentNotValidException e, HttpServletRequest request){
        log.error("[ValidationException] url: {} | errorMessage: {}",
                request.getRequestURL(), e.getMessage());

        // 유효성 검증 실패한 필드 리스트
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();

        StringBuilder errorMessageBuilder = new StringBuilder();
        fieldErrorList.forEach(error -> {
            String field = error.getField();
            String message = error.getDefaultMessage();
            errorMessageBuilder
                    .append(field)
                    .append(" : ")
                    .append(message)
                    .append("; ");
        });
        String errorMessage = errorMessageBuilder.toString();

        ErrorResult errorResult = new ErrorResult(String.valueOf(INVALID_VALUE.getCode()), errorMessage);
        return new ResponseEntity<>(errorResult, INVALID_VALUE.getHttpStatus());
    }

}

