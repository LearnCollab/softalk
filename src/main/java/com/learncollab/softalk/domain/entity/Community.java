package com.learncollab.softalk.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Community extends BaseTime {
    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    private String cm_name;

    //0은 선착순/1은 신청서
    private Integer cm_type;

    private Integer members_limit;

    private Integer members_number;

    //0은 모집중/1은 모집완료
    private Integer state;

    //0은 친목/1은 스터디/2는 .. 일단 3까지 있다고 생각
    private Integer category;

    //설명글
    //private Post Introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member manager;

    @OneToMany(mappedBy = "community")
    private List<CommunityMember> cm_members = new ArrayList<>();

}
