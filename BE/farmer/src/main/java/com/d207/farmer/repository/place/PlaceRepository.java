package com.d207.farmer.repository.place;

import com.d207.farmer.domain.place.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByIdIn(List<Long> placeIds);
}
