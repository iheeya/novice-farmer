package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.UserPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserPlaceRepository extends JpaRepository<UserPlace, Long> {

    @Query("select up from UserPlace up left join fetch up.place p where up.id = :userPlaceId")
    Optional<UserPlace> findByIdWithPlace(@Param("userPlaceId") Long userPlaceId);
}
