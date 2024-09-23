package com.d207.farmer.controller.place;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.dto.place.PlaceRegisterRequestDTO;
import com.d207.farmer.dto.place.PlaceResponseDTO;
import com.d207.farmer.service.place.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/place")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "장소", description = "place")
public class PlaceController {

    private final PlaceService placeService;

    /**
     * 장소 등록
     */
    @Operation(summary = "장소 등록", description = "장소 등록")
    @PostMapping
    public ResponseEntity<String> registerPlace(@RequestBody PlaceRegisterRequestDTO request) {
        log.info("[PlaceController] Received register place request for {}", request);
        return ResponseEntity.created(URI.create("/")).body(placeService.registerPlace(request));
    }

    /**
     * 장소 전체 조회
     */
    @Operation(summary = "장소 전체 조회", description = "장소 전체 조회")
    @GetMapping
    public ResponseEntity<List<PlaceResponseDTO>> getAllPlaces() {
        log.info("[PlaceController] Received get all places request");
        return ResponseEntity.ok(placeService.getAllPlaces());
    }

    /**
     * 장소 개별 조회(by Id)
     */
    @Operation(summary = "장소 개별 조회", description = "장소 개별 조회(by Id)")
    @GetMapping("{id}")
    public ResponseEntity<PlaceResponseDTO> getPlaceById(@PathVariable Long id) {
        log.info("[PlaceController] Received get place request for id {}", id);
        return ResponseEntity.ok(placeService.getPlaceById(id));
    }
}
