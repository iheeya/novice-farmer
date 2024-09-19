package com.d207.farmer.service.user;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.place.PlaceResponseDTO;
import com.d207.farmer.dto.place.PlaceResponseWithIdDTO;
import com.d207.farmer.dto.plant.PlantResponseDTO;
import com.d207.farmer.dto.plant.PlantResponseWithIdDTO;
import com.d207.farmer.dto.survey.SurveyRegisterRequestDTO;
import com.d207.farmer.dto.user.*;
import com.d207.farmer.exception.FailedAuthenticateUserException;
import com.d207.farmer.repository.place.FavoritePlaceRepository;
import com.d207.farmer.repository.place.PlaceRepository;
import com.d207.farmer.repository.plant.FavoritePlantRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.service.place.FavoritePlaceService;
import com.d207.farmer.service.place.PlaceService;
import com.d207.farmer.service.plant.FavoritePlantService;
import com.d207.farmer.service.plant.PlantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PlantService plantService;
    private final PlaceService placeService;
    private final FavoritePlantService favoritePlantService;
    private final FavoritePlantRepository favoritePlantRepository;
    private final FavoritePlaceService favorPlaceService;
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
    public Map<String, List<?>> getSurveyContent() {
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


    public Map<String, List<?>> getSurveyContentWithId(Long userId) {

        List<PlantResponseWithIdDTO> plantResponseWithIdDTOS = plantService.getAllPlantsWithFalse();
        List<PlaceResponseWithIdDTO> placeResponseWithIdDTOS = placeService.getAllPlacesWithFalse();





        List<FavoritePlant> favoritePlants = favoritePlantService.getPlantById(userId);

        // 즐겨찾기 식물의 ID를 Set으로 수집
        Set<Long> favoritePlantIds = favoritePlants.stream()
                .map(favoritePlant -> favoritePlant.getPlant().getId())
                .collect(Collectors.toSet());

        // plantResponseWithIdDTOS의 isOn 값을 업데이트
        for (PlantResponseWithIdDTO dto : plantResponseWithIdDTOS) {
            if (favoritePlantIds.contains(dto.getId())) {
                dto.setIsOn(true); // isOn을 true로 설정
            }
        }
        // isFavorite이 true인 항목이 먼저 오도록 정렬
        plantResponseWithIdDTOS.sort(Comparator.comparing(PlantResponseWithIdDTO::getIsFavorite).reversed()
                .thenComparing(PlantResponseWithIdDTO::getId));
    ///////////////////////



        List<FavoritePlace> favoritePlaces = favorPlaceService.getPlaceById(userId);
        // 즐겨찾기 장소의 ID를 Set으로 수집
        Set<Long> favoritePlaceIds = favoritePlaces.stream()
                .map(favoritePlace -> favoritePlace.getPlace().getId())
                .collect(Collectors.toSet());


        // placeResponseWithIdDTOS의 isOn 값을 업데이트
        for (PlaceResponseWithIdDTO dto : placeResponseWithIdDTOS) {
            if (favoritePlaceIds.contains(dto.getId())) {
                dto.setIsfavorite(true); // isOn을 true로 설정
            } else {
                dto.setIsfavorite(false); // 즐겨찾기가 아닌 경우 false로 설정 (필요할 경우)
            }
        }
        // id 기준으로 정렬
        // 장소 목록 정렬 (isOn이 true인 항목이 먼저 오도록)
        placeResponseWithIdDTOS.sort(Comparator.comparing(PlaceResponseWithIdDTO::getIsFavorite).reversed()
                .thenComparing(PlaceResponseWithIdDTO::getId));

        // 결과를 맵에 담기
        Map<String, List<?>> resultMap = new HashMap<>();
        resultMap.put("plant", plantResponseWithIdDTOS);
        resultMap.put("place", placeResponseWithIdDTOS);

        return resultMap; // 키와 리스트를 포함한 결과 반환
    }
}
