package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunitySelectedTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunitySelectedTagForMainPageRepository extends JpaRepository<CommunitySelectedTag, Long> {
    @Query("select cst from CommunitySelectedTag cst join fetch cst.community where cst.communityTag.id = :communityTagId and cst.community.checkDelete = false " +
            " order by cst.community.writeDate desc")
    List<CommunitySelectedTag> findByCommunityTagId(@Param("communityTagId") Long communityTagId);
}
