package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunityTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityTagForMainPageRepository extends JpaRepository<CommunityTag, Long> {
    List<CommunityTag> findByTagName(String plantName);
}
