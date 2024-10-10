package com.d207.farmer.repository.weekend_farm;

import com.d207.farmer.domain.weekend_farm.WeekendFarm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WeekendFarmRepositoryCustom {
    List<WeekendFarm> findByAddress(String address);
}
