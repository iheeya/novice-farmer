package com.d207.farmer.controller.mongo;

import com.d207.farmer.dto.mongo.place.ImagesAndContentsResponseDTO;
import com.d207.farmer.dto.mongo.place.InfoPlaceNameRequestDTO;
import com.d207.farmer.dto.mongo.place.PlaceInfoResponseDTO;
import com.d207.farmer.dto.mongo.place.PlaceTypeInfoResponseDTO;
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
    public ResponseEntity<List<PlaceTypeInfoResponseDTO>> getPlaceTypeInfo(@RequestHeader("Authorization") String authorization) {
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
                                                                     @RequestBody InfoPlaceNameRequestDTO request) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[InfoController] Received getPlaceInfo request for {}", request);
        return ResponseEntity.ok().body(infoService.getPlaceInfo(request));
    }
}
