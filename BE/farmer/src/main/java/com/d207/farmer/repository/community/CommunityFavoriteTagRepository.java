package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.CommunityComment;
import com.d207.farmer.domain.community.CommunityFavoriteTag;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityFavoriteTagRepository extends JpaRepository<CommunityFavoriteTag, Long> {

    // 반환 타입을 List<CommunityFavoriteTag>로 수정
    List<CommunityFavoriteTag> findByUser(User user);




}
