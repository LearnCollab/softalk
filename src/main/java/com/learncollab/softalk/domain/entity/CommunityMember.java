package com.learncollab.softalk.domain.entity;

import jakarta.persistence.*;

@Entity
public class CommunityMember {
    @Id
    @GeneratedValue
    @Column(name = "communityMember_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "community_id")
    private Community community;

}
