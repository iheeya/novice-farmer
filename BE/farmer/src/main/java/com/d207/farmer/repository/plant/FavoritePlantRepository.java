package com.d207.farmer.repository.plant;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritePlantRepository extends JpaRepository<FavoritePlant, Long> {
    boolean existsByUserAndPlant(User user, Plant plant);
}
