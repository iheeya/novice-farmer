package com.d207.farmer.repository.user;

import com.d207.farmer.domain.user.RecommendPlant;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 샘플 데이터 추가용
 * 원래 fastAPI에서 추가해야함
 */
public interface RecommendPlantRepository extends JpaRepository<RecommendPlant, Long> {
}
