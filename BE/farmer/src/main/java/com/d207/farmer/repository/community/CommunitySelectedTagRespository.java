package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;
import com.d207.farmer.domain.community.CommunitySelectedTag;
import com.d207.farmer.domain.community.CommunityTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunitySelectedTagRespository extends JpaRepository<CommunitySelectedTag, Integer> {
    List<CommunitySelectedTag> findByCommunity(Community community);


    CommunitySelectedTag findByCommunityAndCommunityTag(Community community, CommunityTag communityTag);
}
