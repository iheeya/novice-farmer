package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.Farm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FarmRepository extends JpaRepository<Farm, Long> {
}
