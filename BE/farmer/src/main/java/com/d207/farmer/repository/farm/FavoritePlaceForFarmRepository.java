package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.user.FavoritePlace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritePlaceForFarmRepository extends JpaRepository<FavoritePlace, Long> {
    List<FavoritePlace> findByUserId(Long userId);
}
