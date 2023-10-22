package com.learncollab.softalk.domain.dto.community;

import com.learncollab.softalk.domain.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommunityListResDto {
    private String cm_name;
    private Integer cm_type;
    private Integer members_limit;
    private Integer members_number;
    private Member manager;
    private Integer state;
    private Integer category;
}
