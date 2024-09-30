package com.d207.farmer.repository.mongo;

import com.d207.farmer.domain.mongo.MongoFertilizerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoFertilizerRepository extends MongoRepository<MongoFertilizerInfo, String> {
    MongoFertilizerInfo findFertilizerByName(String name);
}
