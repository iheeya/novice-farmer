package com.d207.farmer.repository.plant;

import aj.org.objectweb.asm.commons.Remapper;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.OptionalDouble;

public interface PlantIllustRepository extends JpaRepository<PlantGrowthIllust, Integer> {

    @Query("select pgi from PlantGrowthIllust pgi where pgi.plant.id = :plantId and pgi.step = :growthStep")
    Optional<PlantGrowthIllust> findByPlantIdAndGrowthStep(@Param("plantId") Long plantId, @Param("growthStep") int growthStep);

    Optional<PlantGrowthIllust> findByPlantAndStep(Plant plant, int growthStep);
}
