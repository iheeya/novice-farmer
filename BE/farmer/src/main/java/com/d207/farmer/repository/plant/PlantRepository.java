package com.d207.farmer.repository.plant;

import com.d207.farmer.domain.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findByIdIn(List<Long> plantIds);
}
