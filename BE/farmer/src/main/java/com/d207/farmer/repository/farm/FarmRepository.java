package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.Farm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FarmRepository extends JpaRepository<Farm, Long>, FarmRepositoryCustom {
}
