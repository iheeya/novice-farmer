package com.d207.farmer.repository.user;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritePlantRepository extends JpaRepository<FavoritePlant, Long> {
    boolean existsByUserAndPlant(User user, Plant plant);

    List<FavoritePlant> findByUser(User user); // 사용자에 따라 즐겨찾기한 식물 목록 조회

    @Modifying
    @Query("delete from FavoritePlant fp where fp.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);

}
