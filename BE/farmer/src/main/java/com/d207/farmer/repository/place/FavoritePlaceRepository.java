package com.d207.farmer.repository.place;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FavoritePlaceRepository extends JpaRepository<FavoritePlace, Long> {
    boolean existsByUserAndPlace(User user, Place place);
}
