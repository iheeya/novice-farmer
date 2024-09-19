package com.d207.farmer.service.user;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.place.PlaceResponseDTO;
import com.d207.farmer.dto.plant.PlantResponseDTO;
import com.d207.farmer.dto.survey.SurveyRegisterRequestDTO;
import com.d207.farmer.dto.user.*;
import com.d207.farmer.exception.FailedAuthenticateUserException;
import com.d207.farmer.repository.place.FavoritePlaceRepository;
import com.d207.farmer.repository.place.PlaceRepository;
import com.d207.farmer.repository.plant.FavoritePlantRepository;
import com.d207.farmer.repository.plant.PlantRepository;
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
    private final FavoritePlantRepository favoritePlantRepository;
    private final PlantRepository plantRepository;
    private final PlaceRepository placeRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;

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
        if (check_firstLogin) {
            user.setIsFirstLogin(false);
        }

        if (user == null) {
            throw new FailedAuthenticateUserException("아이디 혹은 비밀번호가 일치하지 않습니다.");
        }
        return tokenService.saveRefreshToken(user.getId(), check_firstLogin);
    }

    @Transactional
    public Map<String, List<?>> survey() {
        List<PlantResponseDTO> plantResponseDTOList = plantService.getAllPlants();
        List<PlaceResponseDTO> placeResponseDTOList = placeService.getAllPlaces();
        // UserSurveyResponseDTO를 생성하는 로직 추가
        plantResponseDTOList.add(new PlantResponseDTO((long) 0, "없음", 0, false));

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

    @Transactional
    public String registerSurvey(Long userId, SurveyRegisterRequestDTO surveyRegisterRequestDTO) {

        // id가 0인 Plant와 Place 여부를 확인하기 위한 boolean 변수
        boolean hasPlantWithIdZero = false;
        boolean hasPlaceWithIdZero = false;
        User user = userRepository.findById(userId).orElseThrow();
        // Plant 리스트에서 id가 0인지 확인
        for (SurveyRegisterRequestDTO.Plant plant : surveyRegisterRequestDTO.getPlant()) {
            if (plant.getId() == 0) {
                hasPlantWithIdZero = true; // id가 0인 경우 발견
                break; // 반복 종료
            }


            Plant plantDomain = plantRepository.findById(plant.getId()).orElseThrow();

            // 중복 체크
            if (!favoritePlantRepository.existsByUserAndPlant(user, plantDomain)) {
                FavoritePlant favoritePlant = new FavoritePlant(user, plantDomain);
                favoritePlantRepository.save(favoritePlant);
            }

        }

        // Place 리스트에서 id가 0인지 확인
        for (SurveyRegisterRequestDTO.Place place : surveyRegisterRequestDTO.getPlace()) {
            if (place.getId() == 0) {
                hasPlaceWithIdZero = true; // id가 0인 경우 발견
                break; // 반복 종료
            }
            Place placeDomain = placeRepository.findById(place.getId()).orElseThrow();
            // 중복 체크
            if (!favoritePlaceRepository.existsByUserAndPlace(user, placeDomain)) {
                FavoritePlace favoritePlace = new FavoritePlace(user, placeDomain);
                favoritePlaceRepository.save(favoritePlace);
            }

        }
        return "registered successfully.";

    }


}
