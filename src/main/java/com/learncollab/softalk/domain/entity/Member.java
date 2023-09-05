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

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();


    public Member(JoinDto joinDto, PasswordEncoder encoder) {
        this.password = encoder.encode(joinDto.getPassword());
        this.email = joinDto.getEmail();
        this.name = joinDto.getName();
        this.joinDate = LocalDate.now();
        this.getRoles().add("USER");
    }

}
