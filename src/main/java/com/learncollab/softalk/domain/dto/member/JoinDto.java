package com.learncollab.softalk.domain.dto.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinDto {
    private String email;
    private String password;
    private String name;
}
