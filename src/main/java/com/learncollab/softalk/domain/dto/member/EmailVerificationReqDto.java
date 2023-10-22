package com.learncollab.softalk.domain.dto.member;

import lombok.Getter;
import lombok.NoArgsConstructor;

public class EmailVerificationReqDto {

    // 이메일 인증번호 전송
    @Getter
    @NoArgsConstructor
    public static class sendCodeRequest {
        private String email;
    }

}
