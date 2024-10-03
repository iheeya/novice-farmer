package com.d207.farmer.service.mongo;

import com.d207.farmer.domain.mongo.MongoPlantInfo;
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

    public List<MongoPlantInfo> getPlantInfo() {
        return mongoPlantRepository.findAll();
    }
}
