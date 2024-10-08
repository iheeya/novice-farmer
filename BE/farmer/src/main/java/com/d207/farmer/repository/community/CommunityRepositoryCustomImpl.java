package com.d207.farmer.repository.community;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import com.d207.farmer.domain.community.Community;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

// 이거 왜 추가지 ?
import static com.d207.farmer.domain.community.QCommunity.community;
import static com.d207.farmer.domain.community.QCommunitySelectedTag.communitySelectedTag;
@Repository
@RequiredArgsConstructor
public class CommunityRepositoryCustomImpl implements CommunityRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    /**
     * communityTagIds 커뮤니티 조회
     *
     *
     */

    @Override
    public Page<Community> findAllByOrderByWriteDateDesc(List<Long> communityTagIds, Pageable pageable) {

        // 커뮤니티 조회
        List<Community> communities = queryFactory
                .selectFrom(community)
                .join(communitySelectedTag).on(communitySelectedTag.community.eq(community))
                .where(communitySelectedTag.communityTag.id.in(communityTagIds))
                .where(community.checkDelete.eq(false)) // checkDelete가 false인 커뮤니티만 조회 ///
                .orderBy(community.writeDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        // gpt 이용! 페이징 처리!
        // 전체 커뮤니티 수를 조회하여 페이징 처리
        long total = queryFactory
                .selectFrom(community)
                .join(communitySelectedTag).on(communitySelectedTag.community.eq(community))
                .where(communitySelectedTag.communityTag.id.in(communityTagIds))
                .where(community.checkDelete.eq(false)) // checkDelete가 false인 커뮤니티 수 조회 ///
                .fetchCount();

        return new PageImpl<>(communities, pageable, total);
    }

    @Override
    public Page<Community> findByTitleContainingOrContentContaining(String search, Pageable pageable) {
        // 커뮤니티 조회
        List<Community> communities = queryFactory
                .selectFrom(community)
                .where(community.title.contains(search) // 타이틀에 검색어 포함
                        .or(community.content.contains(search))) // 콘텐츠에 검색어 포함
                .where(community.checkDelete.eq(false)) // checkDelete가 false인 커뮤니티만 조회 ///
                .orderBy(community.writeDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // 전체 커뮤니티 수를 조회하여 페이징 처리
        long total = queryFactory
                .selectFrom(community)
                .where(community.title.contains(search)
                        .or(community.content.contains(search))) // 타이틀 또는 콘텐츠에 검색어 포함
                .where(community.checkDelete.eq(false)) // checkDelete가 false인 커뮤니티 수 조회 ///
                .fetchCount();

        return new PageImpl<>(communities, pageable, total);
    }


}