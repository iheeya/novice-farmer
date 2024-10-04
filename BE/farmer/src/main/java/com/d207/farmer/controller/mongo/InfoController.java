package com.d207.farmer.controller.mongo;

import com.d207.farmer.dto.mongo.place.PlaceInfoResponseDTO;
import com.d207.farmer.service.mongo.InfoService;
import com.d207.farmer.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
