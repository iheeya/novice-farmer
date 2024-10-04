package com.d207.farmer.controller.mongo;

import com.d207.farmer.dto.mongo.place.ImagesAndContentsResponseDTO;
import com.d207.farmer.dto.mongo.place.InfoNameRequestDTO;
import com.d207.farmer.dto.mongo.place.PlaceInfoResponseDTO;
import com.d207.farmer.dto.mongo.place.TypeInfoResponseDTO;
import com.d207.farmer.service.mongo.InfoService;
import com.d207.farmer.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/info")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "정보", description = "/info")
public class InfoController {

    private final InfoService infoService;
    private final JWTUtil jwtUtil;

    /**
     * 텃밭 정보 페이지
     */
    @Operation(summary = "텃밭 정보 페이지", description = "텃밭 정보 페이지(info 페이지 접속 시와 동일")
    @GetMapping("/place")
    public ResponseEntity<PlaceInfoResponseDTO> getPlaceMainInfo(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getPlaceMainInfo request for {}", userId);
        return ResponseEntity.ok().body(infoService.getPlaceMainInfo());
    }

    /**
     * 텃밭 종류 조회
     */
    @Operation(summary = "텃밭 종류 조회", description = "텃밭 종류 조회")
    @GetMapping("/place/type")
    public ResponseEntity<List<TypeInfoResponseDTO>> getPlaceTypeInfo(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getPlaceTypeInfo request for {}", userId);
        return ResponseEntity.ok().body(infoService.getPlaceTypeInfo());
    }

    /**
     * 각 텃밭 조회
     */
    @Operation(summary = "각 텃밭 조회", description = "각 텃밭 조회(이름)")
    @PostMapping("/place/type")
    public ResponseEntity<ImagesAndContentsResponseDTO> getPlaceInfo(@RequestHeader("Authorization") String authorization,
                                                                     @RequestBody InfoNameRequestDTO request) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getPlaceInfo request for {}", request);
        return ResponseEntity.ok().body(infoService.getPlaceInfo(request));
    }

    /**
     * 작물 정보 페이지
     */
    @Operation(summary = "작물 정보 페이지", description = "작물 정보 페이지")
    @GetMapping("/plant")
    public ResponseEntity<List<TypeInfoResponseDTO>> getPlantTypeInfo(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getPlantTypeInfo request for {}", userId);
        return ResponseEntity.ok().body(infoService.getPlantTypeInfo());
    }

    /**
     * 각 작물 조회
     */
    @Operation(summary = "각 작물 조회", description = "각 작물 조회(이름)")
    @PostMapping("/plant")
    public ResponseEntity<ImagesAndContentsResponseDTO> getPlantInfo(@RequestHeader("Authorization") String authorization,
                                                                     @RequestBody InfoNameRequestDTO request) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getPlantInfo request for {}", request);
        return ResponseEntity.ok().body(infoService.getPlantInfo(request));
    }

    /**
     * 비료 종류 조회
     */
    @Operation(summary = "비료 종류 조회", description = "비료 종류 조회")
    @GetMapping("/fertilizer")
    public ResponseEntity<List<TypeInfoResponseDTO>> getFertilizerTypeInfo(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getFertilizerTypeInfo request for {}", userId);
        return ResponseEntity.ok().body(infoService.getFertilizerTypeInfo());
    }

    /**
     * 각 비료 조회
     */
    @Operation(summary = "각 비료 조회", description = "각 비료 조회(이름)")
    @PostMapping("/fertilizer")
    public ResponseEntity<ImagesAndContentsResponseDTO> getFertilizerInfo(@RequestHeader("Authorization") String authorization,
                                                                          @RequestBody InfoNameRequestDTO request) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getFertilizerInfo request for {}", request);
        return ResponseEntity.ok().body(infoService.getFertilizerInfo(request));
    }

    /**
     * 병해충 종류 조회
     */
    @Operation(summary = "병해충 종류 조회", description = "병해충 종류 조회")
    @GetMapping("/pest")
    public ResponseEntity<List<TypeInfoResponseDTO>> getPestTypeInfo(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getPestTypeInfo request for {}", userId);
        return ResponseEntity.ok().body(infoService.getPestTypeInfo());
    }
}
