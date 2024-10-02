package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.CommunityComment;
import com.d207.farmer.domain.community.CommunityFavoriteTag;
import com.d207.farmer.domain.community.CommunityTag;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommunityFavoriteTagRepository extends JpaRepository<CommunityFavoriteTag, Long> {

    // 반환 타입을 List<CommunityFavoriteTag>로 수정
    List<CommunityFavoriteTag> findByUser(User user);


    @Query("SELECT CFT.communityTag.id FROM CommunityFavoriteTag CFT " +
            "GROUP BY CFT.communityTag.id " +
            "ORDER BY COUNT(CFT) DESC limit 5")
    List<Long> findTop5FavoriteTagIds();


    CommunityFavoriteTag findByUserAndCommunityTag(User user, CommunityTag communityTag);
}
