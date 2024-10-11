package com.d207.farmer.controller.farm;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.dto.farm.get.*;
import com.d207.farmer.dto.farm.register.FarmRegisterInMyPlaceRegisterDTO;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import com.d207.farmer.service.farm.FarmService;
import com.d207.farmer.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "내 농장(작물)", description = "farm")
public class FarmController {

    private final FarmService farmService;
    private final JWTUtil jwtUtil;

    /**
     * 농장 등록
     */
    @Operation(summary = "농장 등록", description = "농장 등록 버튼")
    @PostMapping
    public ResponseEntity<String> registerFarm(@RequestHeader("Authorization") String authorization,
                                               @RequestBody @Valid FarmRegisterRequestDTO request) {
        log.info("[FarmController] Received register farm request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        Farm farm = farmService.registerFarm(userId, request);
        return ResponseEntity.created(URI.create("/")).body("농장 생성 완료");
    }

    /**
     * 내 텃밭에서 작물 등록
     */
    @Operation(summary = "내 텃밭에서 작물 등록", description = "내 텃밭 페이지에서 작물 등록하기")
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
    @Operation(summary = "장소 조회(with 즐겨찾기)", description = "장소 리스트 조회인데, 추천은 받지않은 장소 -> 즐겨찾기만 있음")
    @GetMapping("/place")
    public ResponseEntity<List<PlaceWithFavoriteResponseDTO>> getPlaceWithFavorite(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get places with favorite request for {}", userId);
        return ResponseEntity.ok().body(farmService.getPlacesWithFavorite(userId));
    }

    /**
     * 작물 조회(with 즐겨찾기)
     */
    @Operation(summary = "작물 조회(with 즐겨찾기)", description = "작물 리스트 조회인데, 추천은 받지않은 작물 -> 즐겨찾기만 있음")
    @GetMapping("/plant")
    public ResponseEntity<List<PlantWithFavoriteResponseDTO>> getPlantWithFavorite(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get plants with favorite request for {}", userId);
        return ResponseEntity.ok().body(farmService.getPlantsWithFavorite(userId));
    }

    /**
     * 장소 조회(with 추천, 즐겨찾기)
     */
    @Operation(summary = "장소 조회(with 추천, 즐겨찾기)", description = "장소 리스트 조회인데, 추천과 즐겨찾기 모두 있는 장소")
    @PostMapping("/place/recommend")
    public ResponseEntity<List<PlaceWithRecommendAndFavoriteResponseDTO>> getPlaceWithRecommendAndFavorite(@RequestHeader("Authorization") String authorization,
                                                                                                           @RequestBody @Valid RecommendPlaceRequestDTO request) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get places with recommend and favorite request for {}", request);

        farmService.requestPlaceRecommend(userId, request);

        return ResponseEntity.ok().body(farmService.getPlacesWithRecommendAndFavorite(userId));
    }

    /**
     * 작물 조회(with 추천, 즐겨찾기)
     */
    @Operation(summary = "작물 조회(with 추천, 즐겨찾기)", description = "작물 리스트 조회인데, 추천과 즐겨찾기 모두 있는 작물")
    @PostMapping("/plant/recommend")
    public ResponseEntity<List<PlantWithRecommendAndFavoriteResponseDTO>> getPlantWithRecommendAndFavorite(@RequestHeader("Authorization") String authorization,
                                                                                                           @RequestBody @Valid RecommendPlantRequestDTO request) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmController] Received get plants with recommend and favorite request for {}", request);

        farmService.requestPlantRecommend(userId, request);

        return ResponseEntity.ok().body(farmService.getPlantsWithRecommendAndFavorite(userId));
    }
}
