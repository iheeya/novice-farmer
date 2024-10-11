package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;
import com.d207.farmer.domain.community.CommunityHeart;
import com.d207.farmer.domain.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CommunityHeartRepository extends CrudRepository<CommunityHeart, Long>, CommunityHeartRepositoryCustom {
    Optional<CommunityHeart> findByCommunityAndUser(Community community, User user);

    // Community에 속한 모든 CommunityHeart를 반환하는 메서드
    List<CommunityHeart> findByCommunity(Community community);


    CharSequence findByCommunityAndUserId(Community community, Long userId);

    Object countByCommunity(Community community);


}
