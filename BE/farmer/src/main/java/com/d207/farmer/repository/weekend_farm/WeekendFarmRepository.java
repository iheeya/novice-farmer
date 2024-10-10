package com.d207.farmer.repository.weekend_farm;

import com.d207.farmer.domain.weekend_farm.WeekendFarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WeekendFarmRepository extends JpaRepository<WeekendFarm, Long>, WeekendFarmRepositoryCustom {
}
