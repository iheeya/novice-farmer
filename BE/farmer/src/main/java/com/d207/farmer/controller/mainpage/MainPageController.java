package com.d207.farmer.controller.mainpage;

import com.d207.farmer.dto.mainpage.MainPageResponseDTO;
import com.d207.farmer.service.mainpage.MainPageService;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mainpage")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MainPageController {

    private final MainPageService mainPageService;
    private final JWTUtil jwtUtil;
    /**
     * 메인페이지 조회
     */
    @GetMapping
    public ResponseEntity<MainPageResponseDTO> getMainPage(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[MainPageController] Received get mainpage request for {}", userId);
        return ResponseEntity.ok().body(mainPageService.getMainPage(userId));
    }
}
