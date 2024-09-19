package com.d207.farmer.repository.user;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, Long> {
    boolean existsByUserAndPlace(User user, Place place);

    List<FavoritePlace> findByUser(User user); // 사용자에 따라 즐겨찾기한 Place 목록 조회

}
