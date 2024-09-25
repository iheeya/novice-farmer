package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommunityCommentForMainPageRepository extends JpaRepository<CommunityComment, Long> {
    @Query("select cc from CommunityComment cc join fetch cc.community where cc.community.id in :communityIds")
    List<CommunityComment> findByCommunityIdIn(@Param("communityIds") List<Long> communityIds);
}
