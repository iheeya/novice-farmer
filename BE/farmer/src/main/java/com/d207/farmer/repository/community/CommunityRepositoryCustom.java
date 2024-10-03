package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
public interface CommunityRepositoryCustom {
    Page<Community> findAllByOrderByWriteDateDesc(List<Long> communityTagIds, Pageable pageable);

    Page<Community> findByTitleContainingOrContentContaining(String search, Pageable pageable);



}
