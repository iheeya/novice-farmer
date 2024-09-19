package com.d207.farmer.controller.user;

import com.d207.farmer.dto.plant.PlantResponseDTO;
import com.d207.farmer.dto.survey.SurveyRegisterRequestDTO;
import com.d207.farmer.dto.user.*;
import com.d207.farmer.service.user.UserService;
import com.d207.farmer.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Value("${spring.jwt.salt}")
    private String salt;

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

    /**
     * 설문조사-공간, 작물(불러오기)
     */
    @GetMapping("/survey")
    public ResponseEntity<Map<String, List<?>>> getSurveyContent() {

        log.info("[UserController] Received get survey after user first login");
        return ResponseEntity.ok().body(userService.getSurveyContent());
    }


    /**
     * 설문조사(선택 후 제출)
     */
    @PostMapping("/survey")
    //public ResponseEntity<Map<String, List<?>>> savesurvey() {
    public ResponseEntity<?> registerSurvey(@RequestHeader("Authorization") String authorization,
                                            @RequestBody SurveyRegisterRequestDTO surveyRegisterRequestDTO) {
        Long userId;
        userId = jwtUtil.getUserId(authorization);

        //create의 ret urn
        return ResponseEntity.created(URI.create("/survey")).body(userService.registerSurvey(userId, surveyRegisterRequestDTO));
    }

    /**
     * 마이페이지 - 선호 텃밭, 작물 불러오기 물어보기!
     */

    @GetMapping("/mypage/like")
    public ResponseEntity<Map<String, List<?>>> getSurveyContentWithId(@RequestHeader("Authorization") String authorization) {

        Long userId;
        userId = jwtUtil.getUserId(authorization);
        log.info("[UserController] Received get mypage- mylike");
        return ResponseEntity.ok().body(userService.getSurveyContentWithId(userId));
    }


    /**
     * 마이페이지 - 선호 텃밭, 작물 수정하기!!!
     */

    @PostMapping("/mypage/like")
    public ResponseEntity<String> registerSurveyContentWithId(@RequestHeader("Authorization") String authorization,
                                                              @RequestBody SurveyRegisterRequestDTO surveyRegisterRequestDTO) {

        Long userId = jwtUtil.getUserId(authorization);

        return ResponseEntity.ok().body(userService.registerSurveyContentWithId(userId, surveyRegisterRequestDTO));
    }






    @PostMapping("/test")
    public ResponseEntity<UserLoginResponseDTO> loginUser2() {

        //log.info("[Us1232323");
        System.out.println("hi~~~~~~~~~~~~~");
        return ResponseEntity.ok().body(null);

    }


}
