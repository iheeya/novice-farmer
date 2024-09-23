package com.d207.farmer.controller.community;

import com.d207.farmer.dto.community.CommunityRegisterDTO;
import com.d207.farmer.dto.community.CommunityResponseDTO;
import com.d207.farmer.dto.place.PlaceRegisterRequestDTO;
import com.d207.farmer.dto.user.UserInfoResponseDTO;
import com.d207.farmer.service.community.CommunityService;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/community")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CommunityController {

    private final CommunityService communityService;
    private final JWTUtil jwtUtil;

    @GetMapping
    public ResponseEntity<List<CommunityResponseDTO>> getCommunityNew(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[CommunityController] Received Community");

        return ResponseEntity.created(URI.create("/")).body(communityService.getCommunity());
    }

    @PostMapping
    public ResponseEntity<String> registerCommunity(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody CommunityRegisterDTO communityRegisterDTO) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("CommunityController] Post Community {}", authorization);

        return ResponseEntity.created(URI.create("/")).body(communityService.registerCommunity(userId, communityRegisterDTO));
    }



}
