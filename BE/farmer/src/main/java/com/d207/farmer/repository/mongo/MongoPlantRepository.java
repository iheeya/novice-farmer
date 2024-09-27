package com.d207.farmer.repository.mongo;

import com.d207.farmer.dto.mongo.plant.MongoPlantInfoDTO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MongoPlantRepository extends MongoRepository<MongoPlantInfoDTO, String> {
}
