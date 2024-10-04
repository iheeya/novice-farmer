package com.d207.farmer.controller.mongo;

import com.d207.farmer.domain.mongo.MongoFertilizerInfo;
import com.d207.farmer.domain.mongo.MongoPestInfo;
import com.d207.farmer.domain.mongo.MongoPlaceInfo;
import com.d207.farmer.domain.mongo.MongoPlantInfo;
import com.d207.farmer.service.mongo.MongoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mongo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "몽고", description = "/mongo")
public class MongoController {

    private final MongoService mongoService;

    /**
     * PLANT
     */
    @GetMapping("/plant")
    public ResponseEntity<List<MongoPlantInfo>> getPlantInfoByMongo() {
        log.info("[MongoController] Received getPlantInfoByMongo request");
        return ResponseEntity.ok().body(mongoService.findAllPlantInfo());
    }

    @GetMapping("/plant/{plantName}")
    public ResponseEntity<MongoPlantInfo> getPlantInfoByName(@PathVariable("plantName") String name) {
        log.info("[MongoController] Received getPlantInfoByName request");
        return ResponseEntity.ok().body(mongoService.findPlantByName(name));
    }

    /**
     * PLACE
     */
    @GetMapping("/place")
    public ResponseEntity<List<MongoPlaceInfo>> getPlaceInfoByMongo() {
        log.info("[MongoController] Received getPlaceInfoByMongo request");
        return ResponseEntity.ok().body(mongoService.findAllPlaceInfo());
    }

    /**
     * PEST
     */
    @GetMapping("/pest")
    public ResponseEntity<List<MongoPestInfo>> getPestInfoByMongo() {
        log.info("[MongoController] Received getPestInfoByMongo request");
        return ResponseEntity.ok().body(mongoService.findAllPestInfo());
    }

    @GetMapping("/pest/{pestName}")
    public ResponseEntity<MongoPestInfo> getPestInfoByName(@PathVariable("pestName") String name) {
        log.info("[MongoController] Received getPestInfoByName request");
        return ResponseEntity.ok().body(mongoService.findPestByName(name));
    }

    /**
     * FERTILIZER
     */
    @GetMapping("/fertilizer")
    public ResponseEntity<List<MongoFertilizerInfo>> getFertilizerInfoByMongo() {
        log.info("[MongoController] Received getFertilizerInfoByMongo request");
        return ResponseEntity.ok().body(mongoService.findAllFertilizerInfo());
    }

}
