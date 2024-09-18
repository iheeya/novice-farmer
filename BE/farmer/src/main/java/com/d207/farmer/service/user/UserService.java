package com.d207.farmer.service.user;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.place.PlaceResponseDTO;
import com.d207.farmer.dto.plant.PlantResponseDTO;
import com.d207.farmer.dto.user.*;
import com.d207.farmer.exception.FailedAuthenticateUserException;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.service.place.PlaceService;
import com.d207.farmer.service.plant.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PlantService plantService;
    private final PlaceService placeService;

    @Transactional
    public UserInfoResponseDTO registerUser(UserRegisterRequestDTO request) {
        User user = new User(request);
        User saveUser = userRepository.save(user);
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regDate(user.getRegDate())
                .isFirstLogin(user.getIsFirstLogin())
                .gender(user.getGender())
                .age(user.getAge())
                .address(user.getAddress())
                .build();
    }

    public UserInfoResponseDTO getUserInfo(UserInfoRequestByEmailDTO request) {
        User user = userRepository.findByEmail(request.getEmail());
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regDate(user.getRegDate())
                .isFirstLogin(user.getIsFirstLogin())
                .gender(user.getGender())
                .age(user.getAge())
                .address(user.getAddress())
                .build();
    }

    public UserInfoResponseDTO getUserInfo(Long userId) {
        Optional<User> optUser = userRepository.findById(userId);
        User user = optUser.orElseThrow();
        return UserInfoResponseDTO.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .regDate(user.getRegDate())
                .isFirstLogin(user.getIsFirstLogin())
                .gender(user.getGender())
                .age(user.getAge())
                .address(user.getAddress())
                .build();
    }

    @Transactional
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO request) {
        User user = userRepository.findByEmailAndPassword(request.getEmail(), request.getPassword());
        boolean check_firstLogin = user.getIsFirstLogin();
        if(check_firstLogin){
            user.setIsFirstLogin(false);
        }

        if(user == null) {
            throw new FailedAuthenticateUserException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
        return tokenService.saveRefreshToken(user.getId(),check_firstLogin);
    }





    public Map<String, List<?>> survey(){
        List<PlantResponseDTO> plantResponseDTOList = plantService.getAllPlants();
        List<PlaceResponseDTO> placeResponseDTOList = placeService.getAllPlaces();
        // UserSurveyResponseDTO를 생성하는 로직 추가
        plantResponseDTOList.add(new PlantResponseDTO((long)0,"없음",0,false));

        // id 기준으로 정렬
        plantResponseDTOList.sort(Comparator.comparing(PlantResponseDTO::getId));

        // PlaceResponseDTO에 "없음" 항목 추가
        placeResponseDTOList.add(new PlaceResponseDTO((long) 0, "없음", "없음", false));

        // id 기준으로 정렬
        placeResponseDTOList.sort(Comparator.comparing(PlaceResponseDTO::getId));

        // 결과를 맵에 담기
        Map<String, List<?>> resultMap = new HashMap<>();
        resultMap.put("plant", plantResponseDTOList);
        resultMap.put("place", placeResponseDTOList);

        return resultMap; // 키와 리스트를 포함한 결과 반환
    }
}
