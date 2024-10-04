package com.d207.farmer.controller.user;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.file.FileUploadTestRequestDTO;
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
     * 회원가입쪽에서 email 중복 여부 체크!!
     */

    @GetMapping("/email-valid")
    public ResponseEntity<?> getEmailUse(@RequestParam("email") String email) {
        log.info("[UserController] check email already use");
        return ResponseEntity.ok().body(userService.getEmailUse(email));
    }

    /**
     * 회원가입쪽에서 nickname 중복 여부 체크!!
     */

    @GetMapping("/nickname-valid")
    public ResponseEntity<?> getNicknameUse(@RequestParam("nickname") String nickname) {
        log.info("[UserController] check nickname already use");
        return ResponseEntity.ok().body(userService.getNicknameUse(nickname));
    }


    /**
     * 일반회원 회원가입
     */
    @PostMapping
    public ResponseEntity<UserInfoResponseDTO> userRegister(@RequestBody @Valid UserRegisterRequestDTO request) {
        log.info("[UserController] Received normal user register request for {}", request);
        return ResponseEntity.created(URI.create("/")).body(userService.registerUser(request));
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
     * 마이페이지 - 선호 텃밭, 작물 변경 후 변경버튼눌렀을때~!!!!
     */

    @PostMapping("/mypage/like")
    public ResponseEntity<String> registerSurveyContentWithId(@RequestHeader("Authorization") String authorization,
                                                              @RequestBody SurveyRegisterRequestDTO surveyRegisterRequestDTO) {

        Long userId = jwtUtil.getUserId(authorization);

        return ResponseEntity.ok().body(userService.registerSurveyContentWithId(userId, surveyRegisterRequestDTO));
    }


    /**
     * 마이페이지!!!! 회원 조회 - token으로 조회
     */

    @GetMapping("/mypage")
    public ResponseEntity<UserInfoResponseDTO> getProfilePage(@RequestHeader("Authorization") String authorization) {
        Long userId;
        userId = jwtUtil.getUserId(authorization);
        log.info("[UserController] Received get mypage- MyProfile");
        return ResponseEntity.ok().body(userService.getUserInfo(userId));
    }


    /**
     * 마이페이지 - 프로필페이지(정보 변경) - Token으로!!!
     */

    @PostMapping("/mypage")
    public ResponseEntity<?> registerProfilePage(@RequestHeader("Authorization") String authorization,
                                                @RequestBody UserInfoRequestDTO userInfoRequestDTO) {
        Long userId;
        userId = jwtUtil.getUserId(authorization);
        log.info("[UserController] Set mypage - MyProfile");
        return ResponseEntity.ok().body(userService.registerUserInfo(userId, userInfoRequestDTO));
    }

    /**
     * 마이페이지 - 이미지변경
     */
    @PostMapping("/mypage/image")
    public ResponseEntity<?> registerUserImage(@RequestHeader("Authorization") String authorization,
                                               @ModelAttribute FileUploadTestRequestDTO request
                                                 ) {
        Long userId;
        userId = jwtUtil.getUserId(authorization);
        log.info("[UserController] Set registerUserImage");

        if(request.getFile().isEmpty()){
            log.info("null@@ {}" ,request.getFile());
            return ResponseEntity.ok().body(userService.registerUserImageClean(userId));
        }
        else {
            return ResponseEntity.ok().body(userService.registerUserImage(userId, request));
        }
    }


    /**
     * 마이페이지 - 과거 기른 작물 출력List<Farm>
     */
    // 확인해봐야함!
    @GetMapping("/mypage/history")
    public ResponseEntity<?> getFarmHistory(@RequestHeader("Authorization") String authorization) {
        Long userId;
        userId = jwtUtil.getUserId(authorization);
        log.info("[UserController] Received get mypage- My Farm history");
        return ResponseEntity.ok().body(userService.getFarmHistory(userId));

    }









    @PostMapping("/imagetest")
    public ResponseEntity<?> imageTest(ModelAttribute modelAttribute) {


        return ResponseEntity.ok().body(null);

    }


}
