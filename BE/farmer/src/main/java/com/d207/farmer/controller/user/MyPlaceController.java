package com.d207.farmer.controller.user;

import com.d207.farmer.dto.myplace.MyPlaceResponseDTO;
import com.d207.farmer.service.user.MyPlaceService;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/myplace")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyPlaceController {

    private final MyPlaceService myPlaceService;
    private final JWTUtil jwtUtil;

    /**
     * 선택된 텃밭과 그 텃밭의 작물들 조회
     */
    @GetMapping
    public ResponseEntity<MyPlaceResponseDTO> getMyPlace(@RequestHeader("Authorization") String authorization,
                                                         @RequestBody MyPlaceRequestDTO request) {
        log.info("[MyPlaceController] Received getMyPlace request for {}", request);
    }
}
