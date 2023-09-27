package com.learncollab.softalk.domain.entity;

import com.learncollab.softalk.domain.dto.member.JoinDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Member{
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String name;
    private LocalDate joinDate;
    private String loginMethod;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    public Member(JoinDto joinDto, PasswordEncoder encoder) {
        this.password = encoder.encode(joinDto.getPassword());
        this.email = joinDto.getEmail();
        this.name = joinDto.getName();
        this.joinDate = LocalDate.now();
        this.loginMethod = "Basic";
        this.getRoles().add("USER");
    }

    public Member(String email, String name, String registrationId){
        this.email = email;
        this.name = name;
        this.joinDate = LocalDate.now();
        this.loginMethod = registrationId;
        this.getRoles().add("USER");
    }

}
