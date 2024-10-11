package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunityFavoriteTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityFavoriteTagForMainPageRepository extends JpaRepository<CommunityFavoriteTag, Long> {
    @Query("select cft from CommunityFavoriteTag cft join fetch cft.communityTag where cft.user.id = :userId")
    List<CommunityFavoriteTag> findByUserId(@Param("userId") Long userId);
}
