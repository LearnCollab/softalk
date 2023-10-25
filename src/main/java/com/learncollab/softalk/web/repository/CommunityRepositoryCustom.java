package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Community;

import java.util.List;

public interface CommunityRepositoryCustom {
    List<Community> searchCommunity(Integer state, Integer category, String cm_name);

    List<Community> communityMainList(Integer state, Integer category);
}
