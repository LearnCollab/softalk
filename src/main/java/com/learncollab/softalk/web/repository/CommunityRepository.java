package com.learncollab.softalk.web.repository;

import com.learncollab.softalk.domain.entity.Community;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityRepository  extends JpaRepository<Community, Long> {
}