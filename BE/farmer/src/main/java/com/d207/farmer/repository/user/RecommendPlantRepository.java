package com.d207.farmer.repository.user;

import com.d207.farmer.domain.user.RecommendPlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecommendPlantRepository extends JpaRepository<RecommendPlant, Long> {

    @Modifying
    @Query("delete from RecommendPlant rp where rp.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
}
