package com.d207.farmer.controller.user;

import com.d207.farmer.domain.farm.TodoType;
import com.d207.farmer.dto.common.FastAPIConnectTestResponseDTO;
import com.d207.farmer.dto.myplant.*;
import com.d207.farmer.service.user.MyPlantService;
import com.d207.farmer.utils.FastApiUtil;
import com.d207.farmer.utils.JWTUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.sql.Update;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/myplant")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "내 작물", description = "myplant")
public class MyPlantController {

    private final MyPlantService myPlantService;
    private final JWTUtil jwtUtil;
    private final FastApiUtil fastApiUtil;

    /**
     * 작물 키우기 시작하기
     */
    @Operation(summary = "작물 키우기 시작하기", description = "작물 키우기 시작하기")
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
    @Operation(summary = "관리 버튼(삭제)", description = "삭제 버튼 클릭")
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
    @Operation(summary = "관리 버튼(첫수확)", description = "첫수확 버튼 클릭")
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
    @Operation(summary = "관리 버튼(종료)", description = "종료 버튼 클릭")
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
    @Operation(summary = "관리 버튼(물주기)", description = "물주기 버튼 클릭")
    @PostMapping("/manage/water")
    public ResponseEntity<String> waterPlant(@RequestHeader("Authorization") String authorization,
                                             @RequestBody ManagePlantRequestDTO request) {
        log.info("[MyPlantController] Received waterPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        String result = myPlantService.waterPlant(userId, request);

        // TODO MVP 발표용 FAST와 통신 단절 후 임의로 칼럼 추가
//        String fastApiResult = myPlantService.updateTodoByFastApi(request.getFarmId(), TodoType.WATERING);

        return ResponseEntity.ok().body(result);
    }

    /**
     * 관리 버튼(비료주기)
     */
    @Operation(summary = "관리 버튼(비료주기)", description = "비료주기 버튼 클릭")
    @PostMapping("/manage/fertilizer")
    public ResponseEntity<String> fertilizerPlant(@RequestHeader("Authorization") String authorization,
                                                  @RequestBody ManagePlantRequestDTO request) {
        log.info("[MyPlantController] Received fertilizerPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.fertilizerPlant(userId, request));
    }

    /**
     * 내 작물 이름 변경
     */
    @Operation(summary = "내 작물 이름 변경", description = "작물 이름 변경(변경 할 이름 보내기)")
    @PostMapping("/name")
    public ResponseEntity<String> updateName(@RequestHeader("Authorization") String authorization,
                                             @RequestBody UpdatePlantNameRequestDTO request) {
        log.info("[MyPlantController] Received updateName request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.updateName(userId, request));
    }

    /**
     * 내 작물 메모 변경
     */
    @Operation(summary = "내 작물 메모 변경", description = "작물 메모 변경(변경 할 메모 보내기)")
    @PostMapping("/memo")
    public ResponseEntity<String> updateMemo(@RequestHeader("Authorization") String authorization,
                                             @RequestBody UpdatePlantMemoRequestDTO request) {
        log.info("[MyPlantController] Received updateMemo request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.updateMemo(userId, request));
    }

    /**
     * 병해충 검사 요청
     */
    @Operation(summary = "병해충 검사 요청", description = "병해충 검사 버튼")
    @GetMapping("/pest")
    public ResponseEntity<InspectionPestResponseDTO> inspectionPest(@RequestHeader("Authorization") String authorization,
                                                                    @RequestBody InspectionPlantRequestDTO request) {
        log.info("[MyPlantController] Received inspectionPest request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.inspectionPest(userId, request));
    }

    /**
     * 생장 정보 업데이트 요청
     */
    @Operation(summary = "생장 정보 업데이트 요청", description = "생장 정보 업데이트 버튼")
    @GetMapping("/growth")
    public ResponseEntity<InspectionGrowthStepResponseDTO> inspectionGrowthStep(@RequestHeader("Authorization") String authorization,
                                                                                @RequestBody InspectionPlantRequestDTO request) {
        log.info("[MyPlantController] Received inspectionGrowthStep request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.inspectionGrowthStep(userId, request));
    }

    /**
     * 생장 정보 업데이트 반영
     * 기능 삭제 예정
     */
    @Operation(summary = "생장 정보 업데이트 반영", description = "생장 정보 업데이트 반영하기")
    @PostMapping("/growth")
    public ResponseEntity<String> updateGrowthStepByInspection(@RequestHeader("Authorization") String authorization,
                                                               @RequestBody UpdateDegreeDayRequestDTO request) {
        log.info("[MyPlantController] Received updateGrowthStepByInspection request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
//        return ResponseEntity.ok().body(myPlantService.updateGrowthStepByInspection(userId, request));
        return ResponseEntity.ok().body("");
    }

    /**
     * 내 작물 상세
     */
    @Operation(summary = "내 작물 상세페이지 조회", description = "내 작물 상세페이지 조회")
    @GetMapping
    public ResponseEntity<MyPlantInfoResponseDTO> getMyPlantInfo(@RequestHeader("Authorization") String authorization,
                                                             @RequestBody MyPlantInfoRequestDTO request) {
        log.info("[MyPlantController] Received getMyPlant request for {}", request);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(myPlantService.getMyPlantInfo(userId, request));
    }

    /**
     * Fast API 통신 테스트
     */
    @GetMapping("/test")
    public ResponseEntity<FastAPIConnectTestResponseDTO> testFastApiConnect() {
        return ResponseEntity.ok().body(fastApiUtil.testFastApiConnect());
    }
}
