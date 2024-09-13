package com.d207.farmer.controller.weekend_farm;

import com.d207.farmer.dto.weekend_farm.WeekendFarmRegisterRequestDTO;
import com.d207.farmer.dto.weekend_farm.WeekendFarmResponseDTO;
import com.d207.farmer.service.weekend_farm.WeekendFarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/weekend")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WeekendFarmController {

    private final WeekendFarmService weekendFarmService;

    /**
     * 주말농장 등록
     */
    @PostMapping
    public ResponseEntity<String> registerWeekendFarm(@RequestBody WeekendFarmRegisterRequestDTO request) {
        log.info("[WeekendFarmController] Received register weekend farm request for {}", request);
        return ResponseEntity.created(URI.create("/")).body(weekendFarmService.registerWeekendFarm(request));
    }

    /**
     * 주말농장 전체 조회
     */
    @GetMapping
    public ResponseEntity<List<WeekendFarmResponseDTO>> getAllWeekendFarms() {
        log.info("[WeekendFarmController] Received get all weekend farms request");
        return ResponseEntity.ok(weekendFarmService.getAllWeekendFarms());
    }

    /**
     * 작물 개별 조회(by Id)
     */
    @GetMapping("{id}")
    public ResponseEntity<WeekendFarmResponseDTO> getWeekendFarm(@PathVariable Long id) {
        log.info("[WeekendFarmController] Received get weekend farm request for {}", id);
        return ResponseEntity.ok(weekendFarmService.getWeekendFarmById(id));
    }
}
