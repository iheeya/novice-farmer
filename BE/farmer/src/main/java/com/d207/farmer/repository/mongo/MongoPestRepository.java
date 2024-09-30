package com.d207.farmer.repository.mongo;

import com.d207.farmer.domain.mongo.MongoPestInfo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoPestRepository extends MongoRepository<MongoPestInfo, String> {
    MongoPestInfo findByName(String name);
}
