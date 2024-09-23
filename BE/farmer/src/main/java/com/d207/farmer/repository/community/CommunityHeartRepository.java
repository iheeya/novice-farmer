package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;
import com.d207.farmer.domain.community.CommunityHeart;
import com.d207.farmer.domain.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CommunityHeartRepository extends CrudRepository<CommunityHeart, Long> {
    Optional<CommunityHeart> findByCommunityAndUser(Community community, User user);
}
