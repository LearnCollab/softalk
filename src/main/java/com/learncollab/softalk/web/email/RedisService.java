package com.learncollab.softalk.web.email;

import com.learncollab.softalk.exception.member.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static com.learncollab.softalk.exception.ExceptionType.VERIFICATION_CODE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;

    // 인증번호 저장 ( key = Email / value = AuthCode )
    public void saveCode(String email, String code, Duration expirationDuration) {
        redisTemplate.opsForValue().set(email, code, expirationDuration);
    }

    public String getCode(String email) {
        String code = redisTemplate.opsForValue().get(email);
        if (code == null) {
            throw new MemberException(VERIFICATION_CODE_NOT_FOUND);
        }
        return code;
    }

}
