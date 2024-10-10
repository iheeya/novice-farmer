package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface  CommunityImageForMainPageRepository extends JpaRepository<CommunityImage, Long> {
    @Query("select ci from CommunityImage ci join fetch ci.community where ci.community.id in :communityIds")
    List<CommunityImage> findByCommunityIdIn(@Param("communityIds") List<Long> communityIds);
}
