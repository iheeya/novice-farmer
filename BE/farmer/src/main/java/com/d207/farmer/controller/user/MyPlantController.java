package com.d207.farmer.controller.user;

import com.d207.farmer.dto.myplant.ManagePlantRequestDTO;
import com.d207.farmer.dto.myplant.StartGrowPlantRequestDTO;
import com.d207.farmer.service.user.MyPlantService;
import com.d207.farmer.utils.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/myplant")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MyPlantController {

    private final MyPlantService myPlantService;
    private final JWTUtil jwtUtil;

    /**
     * 작물 키우기 시작하기
     */
    @PostMapping
    public ResponseEntity<String> startGrowPlant(@RequestHeader("Authorization") String authorization,
                                            @RequestBody StartGrowPlantRequestDTO request) {
        log.info("[MyPlantController] Received startGrowPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.startGrowPlant(userId, request));
    }

    /**
     * 관리 버튼(삭제)
     */
    @PostMapping("/manage/delete")
    public ResponseEntity<String> deletePlant(@RequestHeader("Authorization") String authorization,
                                         @RequestBody ManagePlantRequestDTO request) {
        log.info("[MyPlantController] Received deletePlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.deletePlant(userId, request));
    }

    /**
     * 관리 버튼(첫수확)
     */
    @PostMapping("/manage/harvest")
    public ResponseEntity<String> harvestPlant(@RequestHeader("Authorization") String authorization,
                                          @RequestBody ManagePlantRequestDTO request) {
        log.info("[MyPlantController] Received harvestPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.harvestPlant(userId, request));
    }

    /**
     * 관리 버튼(종료)
     */
    @PostMapping("/manage/end")
    public ResponseEntity<String> endPlant(@RequestHeader("Authorization") String authorization,
                                          @RequestBody ManagePlantRequestDTO request) {
        log.info("[MyPlantController] Received endPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.endPlant(userId, request));
    }

    /**
     * 관리 버튼(물주기)
     */
    @PostMapping("/manage/water")
    public ResponseEntity<String> waterPlant(@RequestHeader("Authorization") String authorization,
                                          @RequestBody ManagePlantRequestDTO request) {
        log.info("[MyPlantController] Received waterPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.waterPlant(userId, request));
    }

    /**
     * 관리 버튼(비료주기)
     */
    @PostMapping("/manage/fertilizer")
    public ResponseEntity<String> fertilizerPlant(@RequestHeader("Authorization") String authorization,
                                          @RequestBody ManagePlantRequestDTO request) {
        log.info("[MyPlantController] Received fertilizerPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.fertilizerPlant(userId, request));
    }

}
