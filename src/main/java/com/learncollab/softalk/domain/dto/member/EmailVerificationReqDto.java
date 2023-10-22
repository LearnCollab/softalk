package com.learncollab.softalk.domain.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class EmailVerificationReqDto {

    // 이메일 인증번호 전송
    @Getter
    @NoArgsConstructor
    public static class sendCodeRequest {

        @NotBlank(message = "이메일은 필수 입력값입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        private String email;

    }

}
