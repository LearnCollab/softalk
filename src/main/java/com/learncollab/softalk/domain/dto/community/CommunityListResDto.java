package com.learncollab.softalk.domain.dto.community;

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
    private String manager;
    private Integer state;
    private Integer category;
}
