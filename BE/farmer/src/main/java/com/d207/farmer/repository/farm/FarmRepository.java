package com.d207.farmer.repository.farm;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FarmRepository extends JpaRepository<Farm, Long> {
    List<Farm> findByUserAndIsCompletedTrue(User user);

}
