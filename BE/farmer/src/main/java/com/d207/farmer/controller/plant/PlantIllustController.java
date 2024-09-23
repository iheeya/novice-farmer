package com.d207.farmer.controller.plant;

import com.d207.farmer.dto.plant.RegisterPlantIllustRequestDTO;
import com.d207.farmer.service.plant.PlantIllustService;
import com.d207.farmer.service.plant.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plant/illust")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PlantIllustController {

    private final PlantIllustService plantIllustService;

    /**
     * 작물 일러스트 등록
     */
    @PostMapping
    public ResponseEntity<String> registerPlantIllust(@RequestBody RegisterPlantIllustRequestDTO request) {
        log.info("[PlantIllustController] Received register plant illust request for {}", request);
        return ResponseEntity.created(URI.create("/plant/illust")).body(plantIllustService.registerPlantIllust(request));
    }
}
