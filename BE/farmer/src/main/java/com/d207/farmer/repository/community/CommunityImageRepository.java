package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;
import com.d207.farmer.domain.community.CommunityImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityImageRepository extends JpaRepository<CommunityImage, Integer> {
    List<CommunityImage> findByCommunity(Community community);

    CommunityImage findByImagePath(String imagePathdto);
}
