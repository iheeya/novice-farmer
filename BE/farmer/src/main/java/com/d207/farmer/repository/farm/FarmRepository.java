package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FarmRepository extends JpaRepository<Farm, Long> {

    /**
     * userPlaceId로 농장 조회
     * 현재 키우기가 진행되고 있는 농장
     * FIXME plant 에서 growthIllust은 어떻게 가져오는지 확인필요
     */
    @Query("select f from Farm f left join fetch f.plant p where f.userPlace.id = :userPlaceId and f.isCompleted = false and f.isDeleted = false")
    Optional<List<Farm>> findByUserPlaceIdWithCurrentGrowing(@Param("userPlaceId") Long userPlaceId);
}
