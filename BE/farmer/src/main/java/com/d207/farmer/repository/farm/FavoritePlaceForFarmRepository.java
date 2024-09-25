package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.user.FavoritePlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritePlaceForFarmRepository extends JpaRepository<FavoritePlace, Long> {
    @Query("select fp from FavoritePlace fp join fetch fp.place p where fp.user.id = :userId")
    List<FavoritePlace> findByUserId(@Param("userId") Long userId);
}
