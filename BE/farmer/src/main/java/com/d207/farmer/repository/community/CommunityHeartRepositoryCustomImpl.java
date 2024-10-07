package com.d207.farmer.repository.community;

import com.d207.farmer.domain.community.Community;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.d207.farmer.domain.community.QCommunity.community;
import static com.d207.farmer.domain.community.QCommunityHeart.communityHeart;
import static com.d207.farmer.domain.community.QCommunitySelectedTag.communitySelectedTag;

@Repository
@RequiredArgsConstructor
public class CommunityHeartRepositoryCustomImpl implements CommunityHeartRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Community> findCommunitiesWithHearts(List<Long> communityTagIds, Pageable pageable) {
        // 하트 수가 있는 커뮤니티 조회
        List<Community> communities = queryFactory
                .selectFrom(community)
                .join(communityHeart).on(communityHeart.community.eq(community))
                .join(communitySelectedTag).on(communitySelectedTag.community.eq(community))
                .where(communitySelectedTag.communityTag.id.in(communityTagIds)) // communityTagIds 조건 추가
                .groupBy(community.id)
                .having(communityHeart.count().gt(0)) // 하트 수가 0보다 큰 경우
                //.orderBy(community.id.desc()) // 원하는 정렬 기준으로 변경 가능
                .orderBy(communityHeart.count().desc())  // 원하는 정렬 기준으로 변경 가능
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 커뮤니티 수를 조회하여 페이징 처리
        long total = queryFactory
                .selectFrom(community)
                .join(communityHeart).on(communityHeart.community.eq(community))
                .join(communitySelectedTag).on(communitySelectedTag.community.eq(community))
                .where(communitySelectedTag.communityTag.id.in(communityTagIds))
                .groupBy(community.id)
                .having(communityHeart.count().gt(0))
                .fetchCount();

        return new PageImpl<>(communities, pageable, total);
    }

    @Override
    public Page<Community> findByTitleContainingOrContentContaining(String search, Pageable pageable) {
        // 검색어가 포함된 커뮤니티 조회
        List<Community> communities = queryFactory
                .selectFrom(community)
                .leftJoin(communityHeart).on(communityHeart.community.eq(community)) // 하트 수를 계산하기 위해 조인
                .where(community.title.contains(search)
                        .or(community.content.contains(search)))
                .groupBy(community.id)
                .orderBy(communityHeart.count().desc()) // 하트 수 기준으로 내림차순 정렬
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 커뮤니티 수 조회
        long total = queryFactory
                .selectFrom(community)
                .leftJoin(communityHeart).on(communityHeart.community.eq(community))
                .where(community.title.contains(search)
                        .or(community.content.contains(search)))
                .groupBy(community.id)
                .fetchCount();

        return new PageImpl<>(communities, pageable, total);
    }
}
