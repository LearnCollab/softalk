package com.learncollab.softalk.web.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learncollab.softalk.domain.dto.member.JwtToken;
import com.learncollab.softalk.domain.dto.member.LoginResDto;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtResponseBuilder {

    public void buildJwtResponse(JwtToken jwtToken, HttpServletResponse response) throws IOException {
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
