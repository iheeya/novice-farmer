package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.user.FavoritePlant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoritePlantForFarmRepository extends JpaRepository<FavoritePlant, Long> {
    @Query("select fp from FavoritePlant fp join fetch fp.plant p where fp.user.id = :userId")
    List<FavoritePlant> findByUserId(@Param("userId") Long userId);
}
