package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.Farm;

import java.util.List;
import java.util.Optional;

public interface FarmRepositoryCustom {
    Optional<List<Farm>> findByUserPlaceIdWithCurrentGrowing(Long userPlaceId);
    Optional<List<Farm>> findByUserIdWithCurrentGrowing(Long userId);
    Optional<Farm> findByIdWithJoin(Long myPlantId);
}
