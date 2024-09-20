package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.user.FavoritePlant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritePlantForFarmRepository extends JpaRepository<FavoritePlant, Long> {
    List<FavoritePlant> findByUserId(Long userId);
}
