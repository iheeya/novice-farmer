package com.d207.farmer.controller.plant;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.dto.plant.PlantRegisterRequestDTO;
import com.d207.farmer.dto.plant.PlantResponseDTO;
import com.d207.farmer.service.plant.PlantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/plant")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "작물", description = "plant")
public class PlantController {

    private final PlantService plantService;

    /**
     * 작물 등록
     */
    @Operation(summary = "작물 등록", description = "작물 등록")
    @PostMapping
    public ResponseEntity<String> registerPlant(@RequestBody PlantRegisterRequestDTO request) {
        log.info("[PlantController] Received register plant request for {}", request);
        return ResponseEntity.created(URI.create("/")).body(plantService.registerPlant(request));
    }

    /**
     * 작물 전체 조회
     */
    @Operation(summary = "작물 전체 조회", description = "작물 전체 조회")
    @GetMapping
    public ResponseEntity<List<PlantResponseDTO>> getAllPlants() {
        log.info("[PlantController] Received get all plants request");
        return ResponseEntity.ok(plantService.getAllPlants());
    }

    /**
     * 작물 개별 조회(by Id)
     */
    @Operation(summary = "작물 개별 조회", description = "작물 개별 조회(by Id)")
    @GetMapping("{id}")
    public ResponseEntity<PlantResponseDTO> getPlant(@PathVariable Long id) {
        log.info("[PlantController] Received get plant request for {}", id);
        return ResponseEntity.ok(plantService.getPlantById(id));
    }
}
