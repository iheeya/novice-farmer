package com.d207.farmer.controller.mongo;

import com.d207.farmer.domain.mongo.MongoPlantInfo;
import com.d207.farmer.service.mongo.MongoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mongo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "몽고", description = "/mongo")
public class MongoController {

    private final MongoService mongoService;

    @GetMapping("/plant")
    public ResponseEntity<List<MongoPlantInfo>> getPlantInfoByMongo() {
        log.info("[MongoController] Received getPlantInfoByMongo request");
        return ResponseEntity.ok().body(mongoService.findAllPlantInfo());
    }
}
