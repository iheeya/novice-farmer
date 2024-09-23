package com.d207.farmer.controller.community;

import com.d207.farmer.dto.place.PlaceRegisterRequestDTO;
import com.d207.farmer.dto.user.UserInfoResponseDTO;
import com.d207.farmer.service.community.CommunityService;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityController {

    private final CommunityService communityService;
    private final JWTUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> getCommunityNew(@RequestHeader("Authorization") String authorization) {
        Long userId;
        userId = jwtUtil.getUserId(authorization);
        log.info("[PlaceController] Received register place request for {}", authorization);

        return ResponseEntity.created(URI.create("/")).body(null);
    }
}
