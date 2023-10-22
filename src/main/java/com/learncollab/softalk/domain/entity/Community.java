package com.learncollab.softalk.domain.entity;

import com.learncollab.softalk.domain.dto.community.CommunityDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
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


    public Community(CommunityDto communityDto) {
        this.cm_name = communityDto.getCm_name();
        this.cm_type = communityDto.getCm_type();
        this.manager = communityDto.getManager();
        this.members_limit = communityDto.getMembers_limit();
        this.members_number = communityDto.getMembers_number();
        this.category = communityDto.getCategory();
        this.state = communityDto.getState();
    }

}
