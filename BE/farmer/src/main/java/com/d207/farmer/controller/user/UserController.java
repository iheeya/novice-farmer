package com.d207.farmer.controller.user;

import com.d207.farmer.dto.user.*;
import com.d207.farmer.service.user.UserService;
import com.d207.farmer.utils.JWTUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    /**
     * 일반회원 회원가입
     */
    @PostMapping
    public ResponseEntity<UserInfoResponseDTO> userRegister(@RequestBody @Valid UserRegisterRequestDTO request) {
        log.info("[UserController] Received normal user register request for {}", request);
        return ResponseEntity.created(URI.create("/")).body(userService.registerUser(request));
    }

    /**
     * 회원 조회 - email로 조회
     * MVP를 위한거임
     */
//    @GetMapping
    public ResponseEntity<UserInfoResponseDTO> getUserInfo(@RequestBody @Valid UserInfoRequestByEmailDTO request) {
        log.info("[UserController] Received get user info by email request for {}", request);
        return ResponseEntity.ok().body(userService.getUserInfo(request));
    }

    /**
     * 회원 조회 - token으로 조회
     */
    @GetMapping
    public ResponseEntity<UserInfoResponseDTO> getUserInfo(@RequestHeader("Authorization") String authorization) {
        log.info("[UserController] Received get user info by token request for {}", authorization);
        Long userId = jwtUtil.getUserId(authorization);
        return ResponseEntity.ok().body(userService.getUserInfo(userId));
    }

    /**
     * 일반회원 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody @Valid UserLoginRequestDTO request) {

        log.info("[UserController] Received get normal user login request for {}", request);

        return ResponseEntity.ok().body(userService.loginUser(request));
    }


}
