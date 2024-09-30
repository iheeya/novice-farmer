package com.d207.farmer.service.mongo;

import com.d207.farmer.domain.mongo.MongoFertilizerInfo;
import com.d207.farmer.domain.mongo.MongoPestInfo;
import com.d207.farmer.domain.mongo.MongoPlaceInfo;
import com.d207.farmer.domain.mongo.MongoPlantInfo;
import com.d207.farmer.repository.mongo.MongoFertilizerRepository;
import com.d207.farmer.repository.mongo.MongoPestRepository;
import com.d207.farmer.repository.mongo.MongoPlaceRepository;
import com.d207.farmer.repository.mongo.MongoPlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MongoService {

    private final MongoPlantRepository mongoPlantRepository;
    private final MongoPlaceRepository mongoPlaceRepository;
    private final MongoPestRepository mongoPestRepository;
    private final MongoFertilizerRepository mongoFertilizerRepository;

    /**
     * PLANT
     */
    public List<MongoPlantInfo> findAllPlantInfo() {
        return mongoPlantRepository.findAll();
    }

    public MongoPlantInfo findPlantByName(String name) {
        return mongoPlantRepository.findByName(name);
    }

    /**
     * PLACE
     */
    public List<MongoPlaceInfo> findAllPlaceInfo() {
        return mongoPlaceRepository.findAll();
    }

    public MongoPlaceInfo findPlaceByName(String name) {
        return mongoPlaceRepository.findByName(name);
    }


    /**
     * PEST
     */
    public List<MongoPestInfo> findAllPestInfo() {
        return mongoPestRepository.findAll();
    }

    public MongoPestInfo findPestByName(String name) {
        return mongoPestRepository.findByName(name);
    }


    /**
     * FERTILIZER
     */
    public List<MongoFertilizerInfo> findAllFertilizerInfo() {
        return mongoFertilizerRepository.findAll();
    }

    public MongoFertilizerInfo findFertilizerByName(String name) {
        return mongoFertilizerRepository.findFertilizerByName(name);
    }

}
