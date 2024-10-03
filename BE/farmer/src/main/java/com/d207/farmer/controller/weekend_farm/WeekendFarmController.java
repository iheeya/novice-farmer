package com.d207.farmer.controller.weekend_farm;

import com.d207.farmer.dto.weekend_farm.WeekendFarmRegisterRequestDTO;
import com.d207.farmer.dto.weekend_farm.WeekendFarmResponseDTO;
import com.d207.farmer.service.weekend_farm.WeekendFarmService;
import com.d207.farmer.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "주말농장", description = "weekend")
public class WeekendFarmController {

    private final WeekendFarmService weekendFarmService;
    private final JWTUtil jwtUtil;

    /**
     * 주말농장 등록
     */
    @Operation(summary = "주말농장 등록", description = "주말농장 등록")
    @PostMapping
    public ResponseEntity<String> registerWeekendFarm(@RequestBody WeekendFarmRegisterRequestDTO request) {
        log.info("[WeekendFarmController] Received register weekend farm request for {}", request);
        return ResponseEntity.created(URI.create("/")).body(weekendFarmService.registerWeekendFarm(request));
    }

    /**
     * 주말농장 전체 조회
     */
    @Operation(summary = "주말농장 전체 조회", description = "주말농장 전체 조회")
    @GetMapping
    public ResponseEntity<List<WeekendFarmResponseDTO>> getAllWeekendFarms() {
        log.info("[WeekendFarmController] Received get all weekend farms request");
        return ResponseEntity.ok(weekendFarmService.getAllWeekendFarms());
    }

    /**
     * 주말농장 개별 조회(by Id)
     */
    @Operation(summary = "주말농장 개별 조회", description = "주말농장 개별 조회(by Id)")
    @GetMapping("{id}")
    public ResponseEntity<WeekendFarmResponseDTO> getWeekendFarm(@PathVariable Long id) {
        log.info("[WeekendFarmController] Received get weekend farm request for {}", id);
        return ResponseEntity.ok(weekendFarmService.getWeekendFarmById(id));
    }

    /**
     * 주말농장 추천(위치 기반)
     */
    @Operation(summary = "주말농장 추천(위치 기반)", description = "주말농장 추천(사용자 위치 기반)")
    @GetMapping("/recommend")
    public ResponseEntity<List<WeekendFarmResponseDTO>> getWeekendFarmRecommend(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[WeekendFarmController] Received getWeekendFarmRecommend request for {}", userId);
        return ResponseEntity.ok(weekendFarmService.getWeekendFarmRecommend(userId));
    }
}
