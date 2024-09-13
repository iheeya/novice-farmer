package com.d207.farmer.repository.redis;

import com.d207.farmer.domain.user.RedisToken;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<RedisToken, Long> {
}
