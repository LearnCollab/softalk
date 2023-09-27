package com.learncollab.softalk.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OAuth2UserAttributes {
    String email;
    String name;
    String registrationId;
}
