package com.learncollab.softalk.domain.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Community extends BaseTime {
    @Id
    @GeneratedValue
    @Column(name = "community_id")
    private Long id;

    private String cm_name;

    //0은 선착순/1은 신청서
    private Integer cm_type;

    private Integer members_limit;

    //설명글
    //private Post Introduction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member manager;

    @OneToMany(mappedBy = "community")
    private List<CommunityMember> cm_members = new ArrayList<>();

}
