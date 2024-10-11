package com.d207.farmer.repository.mongo;

import com.d207.farmer.domain.mongo.MongoPlaceInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPlaceRepository extends MongoRepository<MongoPlaceInfo, String> {
}
