package com.d207.farmer.controller.user;

import com.d207.farmer.dto.myplace.MyPlaceRequestDTO;
import com.d207.farmer.dto.myplace.MyPlaceResponseDTO;
import com.d207.farmer.dto.myplace.UpdateMyPlaceNameRequestDTO;
import com.d207.farmer.service.user.MyPlaceService;
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
@RequestMapping("/myplace")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "내 장소", description = "myplace")
public class MyPlaceController {

    private final MyPlaceService myPlaceService;
    private final JWTUtil jwtUtil;

    /**
     * 선택된 텃밭과 그 텃밭의 작물들 조회
     */
    @Operation(summary = "선택된 텃밭의 작물들 조회", description = "선택된 텃밭과, 그 텃밭의 작물들 조회")
    @GetMapping("{myPlaceId}")
    public ResponseEntity<MyPlaceResponseDTO> getMyPlace(@RequestHeader("Authorization") String authorization,
                                                         @PathVariable("myPlaceId") Long myPlaceId) {
        log.info("[MyPlaceController] Received getMyPlace request for {}", myPlaceId);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlaceService.getMyPlace(userId, myPlaceId));
    }

    /**
     * 내 텃밭 이름 변경
     */
    @Operation(summary = "내 텃밭 이름 변경", description = "텃밭 이름 변경(변경할 이름 보내기)")
    @PostMapping("/name")
    public ResponseEntity<String> updateMyPlaceName(@RequestHeader("Authorization") String authorization,
                                                    @RequestBody UpdateMyPlaceNameRequestDTO request) {
        log.info("[MyPlaceController] Received updateMyPlaceName request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlaceService.updateMyPlaceName(userId, request));
    }
}
