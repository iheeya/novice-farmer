package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;
import com.d207.farmer.domain.community.CommunityTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

public interface CommunityTagRepository extends JpaRepository<CommunityTag, Long> {
    List<CommunityTag> findBytagNameIn(List<String> communityTagList);


    CommunityTag findByTagName(String tag);
}
