package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunityHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityHeartForMainPageRepository extends JpaRepository<CommunityHeart, Long> {
    List<CommunityHeart> findByCommunityIdIn(List<Long> communityIds);
}
