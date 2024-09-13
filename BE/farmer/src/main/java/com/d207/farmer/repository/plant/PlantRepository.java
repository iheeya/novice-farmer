package com.d207.farmer.repository.plant;

import com.d207.farmer.domain.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}
