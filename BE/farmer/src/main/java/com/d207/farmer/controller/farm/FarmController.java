package com.d207.farmer.controller.farm;

import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import com.d207.farmer.service.farm.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/farm")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FarmController {

    private final FarmService farmService;

    /**
     * 농장 등록
     */
    public ResponseEntity<String> registerFarm(@RequestHeader("Authorization") String authorization,
                                               @RequestBody @Valid FarmRegisterRequestDTO request) {
        log.info("[FarmController] Received register farm request for {}", request);
        // TODO 회원 넘기기
        return ResponseEntity.created(URI.create("/")).body(farmService.registerFarm(request));
    }

}
