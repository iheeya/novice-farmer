package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunityHeart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityHeartForMainPageRepository extends JpaRepository<CommunityHeart, Long> {
    @Query("select ch from CommunityHeart ch join fetch ch.community where ch.community.id in :communityIds")
    List<CommunityHeart> findByCommunityIdIn(@Param("communityIds") List<Long> communityIds);
}
