package com.d207.farmer.repository.community;


import com.d207.farmer.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    @Query("select c from Community c left join fetch c.user where c.id = :id")
    Optional<Community> findByIdWithUser(@Param("id") Long id);
}
