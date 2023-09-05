package com.learncollab.softalk.domain.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinDto {
    private String email;
    private String password;
    private String name;
}
