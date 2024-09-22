package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.QFarm;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.d207.farmer.domain.farm.QFarm.farm;

@Repository
@RequiredArgsConstructor
public class FarmRepositoryCustomImpl implements FarmRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * userPlaceId로 농장 조회
     * 현재 키우기가 진행되고 있는 농장
     * FIXME plant 에서 growthIllust은 어떻게 가져오는지 확인필요
     */
    @Query("select f from Farm f left join fetch f.plant p where f.userPlace.id = :userPlaceId and f.isCompleted = false and f.isDeleted = false")
    @Override
    public Optional<List<Farm>> findByUserPlaceIdWithCurrentGrowing(Long userPlaceId) {
        return Optional.of(queryFactory.selectFrom(farm)
                .join(farm.plant).fetchJoin()
                .where(
                        userPlaceIdEq(userPlaceId),
                        isCurrentGrowingFarm()
                )
                .fetch());
    }

    /**
     * 유저아이디로 농장 조회
     * 현재 키우기가 진행되고 있는 농장
     */
    @Override
    public Optional<List<Farm>> findByUserIdWithCurrentGrowing(Long userId) {
        return Optional.of(queryFactory.selectFrom(farm)
                .where(
                        userIdEq(userId),
                        isCurrentGrowingFarm()
                )
                .fetch());
    }

    private BooleanExpression userIdEq(Long userId) {
        return farm.user.id.eq(userId);
    }

    private BooleanExpression userPlaceIdEq(Long userPlaceId) {
        return farm.userPlace.id.eq(userPlaceId);
    }
    private BooleanExpression isCurrentGrowingFarm() {
        return farm.isCompleted.not().and(farm.isDeleted.not());
    }
}
