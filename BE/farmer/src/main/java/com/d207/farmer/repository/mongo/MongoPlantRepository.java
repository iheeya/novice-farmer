package com.d207.farmer.repository.mongo;

import com.d207.farmer.domain.mongo.MongoPlantInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPlantRepository extends MongoRepository<MongoPlantInfo, String> {
    MongoPlantInfo findByName(String name);
}
