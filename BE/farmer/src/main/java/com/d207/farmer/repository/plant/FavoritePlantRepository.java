package com.d207.farmer.repository.plant;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritePlantRepository extends JpaRepository<FavoritePlant, Long> {
    boolean existsByUserAndPlant(User user, Plant plant);

    List<FavoritePlant> findByUser(User user); // 사용자에 따라 즐겨찾기한 식물 목록 조회
}
