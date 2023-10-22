package com.learncollab.softalk.web.email;

import com.learncollab.softalk.exception.member.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.learncollab.softalk.exception.ExceptionType.EMAIL_SEND_ERROR;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender emailSender;

    // 이메일 발송
    public void sendEmail(String email, String authCode) {
        SimpleMailMessage emailForm = createEmailForm(email, authCode);
        try {
            emailSender.send(emailForm);
        } catch (RuntimeException e) {
            throw new MemberException(EMAIL_SEND_ERROR, EMAIL_SEND_ERROR.getCode(), EMAIL_SEND_ERROR.getErrorMessage());
        }
    }

    // 발송할 이메일 데이터 설정
    private SimpleMailMessage createEmailForm(String email, String authCode) {
        SimpleMailMessage message = new SimpleMailMessage();

        String content = "요청하신 인증번호는 [ " + authCode + " ] 입니다.\n" +
                "위의 인증번호를 인증번호 입력창에 입력해 주세요.\n\n" +
                "감사합니다.";

        message.setTo(email);
        message.setSubject("[SOFTALK] 이메일 인증번호");
        message.setText(content);

        return message;
    }
}