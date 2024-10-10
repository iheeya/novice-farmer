package com.d207.farmer.repository.weekend_farm;

import com.d207.farmer.domain.weekend_farm.QWeekendFarm;
import com.d207.farmer.domain.weekend_farm.WeekendFarm;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.d207.farmer.domain.weekend_farm.QWeekendFarm.weekendFarm;

@Repository
@RequiredArgsConstructor
public class WeekendFarmRepositoryCustomImpl implements WeekendFarmRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WeekendFarm> findByAddress(String address) {
        return queryFactory.selectFrom(weekendFarm)
                .where(weekendFarm.address.contains(address))
                .fetch();
    }
}
