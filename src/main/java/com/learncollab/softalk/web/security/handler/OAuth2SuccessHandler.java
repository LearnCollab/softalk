package com.learncollab.softalk.web.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learncollab.softalk.domain.dto.member.JwtToken;
import com.learncollab.softalk.domain.dto.member.LoginResDto;
import com.learncollab.softalk.domain.dto.member.OAuth2UserAttributes;
import com.learncollab.softalk.domain.event.OAuth2UserRegisteredEvent;
import com.learncollab.softalk.web.security.JwtTokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    private final JwtTokenProvider jwtTokenProvider;
    private final ApplicationEventPublisher eventPublisher; // 이벤트 발행기

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2UserAttributes oAuth2UserAttributes = extractOAuth2UserAttributes(authentication);

        saveOAuth2User(oAuth2UserAttributes);

        JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);

        sendJwtTokenResponse(response, jwtToken);
    }

    public OAuth2UserAttributes extractOAuth2UserAttributes(Authentication authentication){
        OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String registrationId = (String) attributes.get("registrationId");
        Map<String, Object> account = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) account.get("email");
        Map<String, Object> profile =  (Map<String, Object>) attributes.get("profile");
        String name = (String) profile.get("nickname");

        return new OAuth2UserAttributes(email, name, registrationId);
    }

    public void saveOAuth2User(OAuth2UserAttributes oAuth2UserAttributes){

        OAuth2UserRegisteredEvent event = new OAuth2UserRegisteredEvent(
                this,
                oAuth2UserAttributes.getEmail(),
                oAuth2UserAttributes.getName(),
                oAuth2UserAttributes.getRegistrationId()
        );
        //이벤트 발행
        eventPublisher.publishEvent(event);

    }

    public void sendJwtTokenResponse(HttpServletResponse response, JwtToken jwtToken) throws IOException {
        String grantType = jwtToken.getGrantType();
        String accessToken = jwtToken.getAccessToken();
        String refreshToken = jwtToken.getRefreshToken();

        response.addHeader("Authorization", grantType + " " + accessToken);

        LoginResDto loginResDto = new LoginResDto(refreshToken);
        response.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(loginResDto);
        response.getWriter().write(jsonString);
    }
}
