package com.d207.farmer.controller.farm;

import com.d207.farmer.dto.farm.todo.FarmTodoResponseDTO;
import com.d207.farmer.service.farm.FarmTodoService;
import com.d207.farmer.utils.JWTUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/todo")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "TODO 조회", description = "todo")
public class FarmTodoController {

    private final FarmTodoService farmTodoService;
    private final JWTUtil jwtUtil;

    /**
     * t ODO 페이지 조회
     */
    @GetMapping
    public ResponseEntity<List<FarmTodoResponseDTO>> getMyFarmTodo(@RequestHeader("Authorization") String authorization) {
        Long userId = jwtUtil.getUserId(authorization);
        log.info("[FarmTodoController] Received getMyFarmTodo request for {}", userId);
        return ResponseEntity.ok().body(farmTodoService.getMyFarmTodo(userId));
    }
}
