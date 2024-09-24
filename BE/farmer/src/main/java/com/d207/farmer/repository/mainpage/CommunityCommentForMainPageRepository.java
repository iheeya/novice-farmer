package com.d207.farmer.repository.mainpage;

import com.d207.farmer.domain.community.CommunityComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityCommentForMainPageRepository extends JpaRepository<CommunityComment, Long> {
}
