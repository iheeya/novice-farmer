package com.d207.farmer.repository.user;

import com.d207.farmer.domain.user.RecommendPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecommendPlaceRepository extends JpaRepository<RecommendPlace, Long> {

    @Modifying
    @Query("delete from RecommendPlace rp where rp.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
