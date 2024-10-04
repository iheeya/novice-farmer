package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommunityHeartRepositoryCustom {
    Page<Community> findCommunitiesWithHearts(List<Long> communityTagIds, Pageable pageable);

    Page<Community> findByTitleContainingOrContentContaining(String search, Pageable pageable);
}
