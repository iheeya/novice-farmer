package com.d207.farmer.controller.farm;

import com.d207.farmer.dto.farm.get.PlaceWithFavoriteResponseDTO;
import com.d207.farmer.dto.farm.get.PlaceWithRecommendAndFavoriteResponseDTO;
import com.d207.farmer.dto.farm.get.PlantWithFavoriteResponseDTO;
import com.d207.farmer.dto.farm.get.PlantWithRecommendAndFavoriteResponseDTO;
import com.d207.farmer.dto.farm.register.FarmRegisterInMyPlaceRegisterDTO;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import com.d207.farmer.service.farm.FarmService;
import com.d207.farmer.utils.JWTUtil;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/farm")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FarmController {

    private final FarmService farmService;
    private final JWTUtil jwtUtil;

    /**
     * 농장 등록
     */
    @PostMapping
    public ResponseEntity<String> registerFarm(@RequestHeader("Authorization") String authorization,
                                               @RequestBody @Valid FarmRegisterRequestDTO request) {
        log.info("[FarmController] Received register farm request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.created(URI.create("/")).body(farmService.registerFarm(userId, request));
    }

    /**
     * 내 텃밭에서 작물 등록
     */
    @PostMapping("/plant")
    public ResponseEntity<String> registerFarm(@RequestHeader("Authorization") String authorization,
                                               @RequestBody @Valid FarmRegisterInMyPlaceRegisterDTO request) {
        log.info("[FarmController] Received register farm in my place request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.created(URI.create("/")).body(farmService.registerFarm(userId, request));
    }

    /**
     * 장소 조회(with 즐겨찾기)
     */
    @GetMapping("/place")
    public ResponseEntity<List<PlaceWithFavoriteResponseDTO>> getPlaceWithFavorite(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get places with favorite request for {}", userId);
        return ResponseEntity.ok().body(farmService.getPlacesWithFavorite(userId));
    }

    /**
     * 작물 조회(with 즐겨찾기)
     */
    @GetMapping("/plant")
    public ResponseEntity<List<PlantWithFavoriteResponseDTO>> getPlantWithFavorite(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get plants with favorite request for {}", userId);
        return ResponseEntity.ok().body(farmService.getPlantsWithFavorite(userId));
    }

    /**
     * 장소 조회(with 추천, 즐겨찾기)
     */
    @GetMapping("/place/recommend")
    public ResponseEntity<List<PlaceWithRecommendAndFavoriteResponseDTO>> getPlaceWithRecommendAndFavorite(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get places with recommend and favorite request for {}", userId);
        return ResponseEntity.ok().body(farmService.getPlacesWithRecommendAndFavorite(userId));
    }

    /**
     * 작물 조회(with 추천, 즐겨찾기)
     */
    @GetMapping("/plant/recommend")
    public ResponseEntity<List<PlantWithRecommendAndFavoriteResponseDTO>> getPlantWithRecommendAndFavorite(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get plants with recommend and favorite request for {}", userId);
        return ResponseEntity.ok().body(farmService.getPlantsWithRecommendAndFavorite(userId));
    }
}
