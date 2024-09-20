package com.d207.farmer.service.farm;

import com.d207.farmer.domain.common.Address;
import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.*;
import com.d207.farmer.dto.farm.api.GeoAPIResponseDTO;
import com.d207.farmer.dto.farm.get.*;
import com.d207.farmer.dto.farm.register.FarmRegisterInMyPlaceRegisterDTO;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.FavoritePlaceForFarmRepository;
import com.d207.farmer.repository.farm.FavoritePlantForFarmRepository;
import com.d207.farmer.repository.farm.UserPlaceRepository;
import com.d207.farmer.repository.place.PlaceRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.user.RecommendPlaceRepository;
import com.d207.farmer.repository.user.RecommendPlantRepository;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.utils.AddressUtil;
import com.d207.farmer.utils.FastApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.ConnectException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final PlaceRepository placeRepository;
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;
    private final UserPlaceRepository userPlaceRepository;
    private final FavoritePlaceForFarmRepository favoritePlaceRepository;
    private final FavoritePlantForFarmRepository favoritePlantRepository;
    private final RecommendPlaceRepository recommendPlaceRepository;
    private final RecommendPlantRepository recommendPlantRepository;
    private final AddressUtil addressUtil;
    private final FastApiUtil fastApiUtil;

    @Transactional
    public String registerFarm(Long userId, FarmRegisterRequestDTO request) {
        // user, place, plant 조회
        User user = userRepository.findById(userId).orElseThrow();
        Place place = placeRepository.findById(request.getPlace().getPlaceId()).orElseThrow();
        Plant plant = plantRepository.findById(request.getPlant().getPlantId()).orElseThrow();

        // userPlace 생성 및 저장
        UserPlace userPlace = createUserPlace(user, place, request);
        UserPlace saveUserPlace = userPlaceRepository.save(userPlace);

        // farm 생성
        Farm farm = new Farm(user, saveUserPlace, plant, request.getPlant());
        farmRepository.save(farm);

        return "농장 생성 완료";
    }

    private UserPlace createUserPlace(User user, Place place, FarmRegisterRequestDTO request) {
        // 위 경도 불러오기
        Map<String, String> latAndLongMap = addressUtil.getLatAndLongByJibun(request.getPlace().getAddress().getJibun());
        String latitude = latAndLongMap.get("latitude");
        String longitude = latAndLongMap.get("longitude");

        Address address = request.getPlace().getAddress();
        // 내장 값타입은 값 변경할 때 생성자로 인스턴스 새로 생성
        // 지번에서 번지 찾아서 address 인스턴스 재생성
        Address newAddress = createAddress(address);

        // userPlace 생성
        return new UserPlace(user, place, latitude, longitude, place.getName(), newAddress, request.getPlace().getDirection());
    }

    private Address createAddress(Address addr) {
        String bunji = addressUtil.findBunjiByJibun(addr.getJibun());
        return new Address(addr.getSido(), addr.getSigugun(), addr.getBname1(), addr.getBname2(),
                bunji, addr.getJibun(), addr.getZonecode());
    }

    @Transactional
    public String registerFarm(Long userId, FarmRegisterInMyPlaceRegisterDTO request) {
        // 회원, 작물 조회
        User user = userRepository.findById(userId).orElseThrow();
        Plant plant = plantRepository.findById(request.getPlant().getPlantId()).orElseThrow();

        // userPlace 조회
        UserPlace userPlace = userPlaceRepository.findById(request.getMyPlaceId()).orElseThrow();

        // farm 생성
        Farm farm = new Farm(user, userPlace, plant, request.getPlant());
        farmRepository.save(farm);

        return "작물 저장 완료";
    }

    public List<PlaceWithFavoriteResponseDTO> getPlacesWithFavorite(Long userId) {
        List<PlaceWithFavoriteResponseDTO> result = new ArrayList<>();

        // 즐겨찾기(장소) 조회
        List<FavoritePlace> favorites = favoritePlaceRepository.findByUserId(userId);

        Set<Long> placeIdSet = favorites.stream().map(f -> f.getPlace().getId()).collect(Collectors.toSet());

        // 장소 조회
        List<Place> places = placeRepository.findAll();

        // result list 생성
        for (Place p : places) {
            boolean isFavorite = false;
            if(placeIdSet.contains(p.getId())) {
                isFavorite = true;
            }
            result.add(new PlaceWithFavoriteResponseDTO(p.getId(), p.getName(), isFavorite, p.getIsOn()));
        }

        result.sort(Comparator.comparing((PlaceWithFavoriteResponseDTO p) -> !p.getIsService())
                .thenComparing(p -> !p.getIsFavorite())
                .thenComparing(PlaceWithFavoriteResponseDTO::getPlaceId));

        return result;
    }

    public List<PlantWithFavoriteResponseDTO> getPlantsWithFavorite(Long userId) {
        List<PlantWithFavoriteResponseDTO> result = new ArrayList<>();

        // 작물 조회
        List<Plant> plants = plantRepository.findAll();

        // 즐겨찾기(작물) 조회
        List<FavoritePlant> favorites = favoritePlantRepository.findByUserId(userId);

        Set<Long> plantIdSet = favorites.stream().map(f -> f.getPlant().getId()).collect(Collectors.toSet());

        // result list 생성
        for (Plant p : plants) {
            boolean isFavorite = false;
            if(plantIdSet.contains(p.getId())) {
                isFavorite = true;
            }
            result.add(new PlantWithFavoriteResponseDTO(p.getId(), p.getName(), isFavorite, p.getIsOn()));
        }

        result.sort(Comparator.comparing((PlantWithFavoriteResponseDTO p) -> !p.getIsService())
                .thenComparing(p -> !p.getIsFavorite())
                .thenComparing(PlantWithFavoriteResponseDTO::getPlantId));

        return result;
    }

    public List<PlaceWithRecommendAndFavoriteResponseDTO> getPlacesWithRecommendAndFavorite(Long userId) {
        List<PlaceWithRecommendAndFavoriteResponseDTO> result = new ArrayList<>();

        // 장소 조회
        List<Place> places = placeRepository.findAll();

        // 즐겨찾기(장소) 조회
        List<FavoritePlace> favorites = favoritePlaceRepository.findByUserId(userId);

        // 즐겨찾기 아이디 set 생성
        Set<Long> favoriteIdSet = favorites.stream().map(f -> f.getPlace().getId()).collect(Collectors.toSet());

        // 추천(장소) 조회
        List<RecommendPlace> recommends = recommendPlaceRepository.findAll();

        // 추천 아이디 set 생성
        Set<Long> recommendIdSet = recommends.stream().map(r -> r.getPlace().getId()).collect(Collectors.toSet());

        // result list 생성
        for (Place p : places) {
            boolean isFavorite = false;
            boolean isRecommend = false;
            if(favoriteIdSet.contains(p.getId())) {
                isFavorite = true;
            }
            if(recommendIdSet.contains(p.getId())) {
                isRecommend = true;
            }
            result.add(new PlaceWithRecommendAndFavoriteResponseDTO(p.getId(), p.getName(), isFavorite, isRecommend, p.getIsOn()));
        }
        result.sort(Comparator.comparing((PlaceWithRecommendAndFavoriteResponseDTO p) -> !p.getIsService())
                .thenComparing(p -> !p.getIsRecommend())
                .thenComparing(p -> !p.getIsFavorite())
                .thenComparing(PlaceWithRecommendAndFavoriteResponseDTO::getPlaceId));

        return result;
    }

    public List<PlantWithRecommendAndFavoriteResponseDTO> getPlantsWithRecommendAndFavorite(Long userId) {
        List<PlantWithRecommendAndFavoriteResponseDTO> result = new ArrayList<>();

        // 작물 조회
        List<Plant> plants = plantRepository.findAll();

        // 즐겨찾기(작물) 조회
        List<FavoritePlant> favorites = favoritePlantRepository.findByUserId(userId);

        // 즐겨찾기 아이디 set 생성
        Set<Long> favoriteIdSet = favorites.stream().map(f -> f.getPlant().getId()).collect(Collectors.toSet());

        // 추천(작물) 조회
        List<RecommendPlant> recommends = recommendPlantRepository.findAll();

        // 추천 아이디 set 생성
        Set<Long> recommendIdSet = recommends.stream().map(r -> r.getPlant().getId()).collect(Collectors.toSet());

        // result list 생성
        for (Plant p : plants) {
            boolean isFavorite = false;
            boolean isRecommend = false;
            if(favoriteIdSet.contains(p.getId())) {
                isFavorite = true;
            }
            if(recommendIdSet.contains(p.getId())) {
                isRecommend = true;
            }
            result.add(new PlantWithRecommendAndFavoriteResponseDTO(p.getId(), p.getName(), isFavorite, isRecommend, p.getIsOn()));
        }

        result.sort(Comparator.comparing((PlantWithRecommendAndFavoriteResponseDTO p) -> !p.getIsService())
                .thenComparing(p -> !p.getIsRecommend())
                .thenComparing(p -> !p.getIsFavorite())
                .thenComparing(PlantWithRecommendAndFavoriteResponseDTO::getPlantId));
        return result;
    }

    @Transactional
    public void requestPlaceRecommend(Long userId, RecommendPlaceRequestDTO request) {
        // 작물 조회
        Plant plant = plantRepository.findById(request.getPlantId()).orElseThrow();

        // 회원 조회
        User user = userRepository.findById(userId).orElseThrow();

        // 추천 장소 받기
        RecommendPlaceResponseDTO response = fastApiUtil.getRecommendPlaceByFastApi(request, plant);
        List<RecommendPlaceResponseDTO.placeDTO> placeDTOs = response.getPlaces();

        // 먼저 추천 테이블 비우기(by userId)
        recommendPlaceRepository.deleteByUserId(userId);

        // 추천 테이블에 추가
        List<Long> placeIds = new ArrayList<>();
        for (RecommendPlaceResponseDTO.placeDTO p : placeDTOs) {
            placeIds.add(p.getPlaceId());
        }

        List<Place> places = placeRepository.findByIdIn(placeIds);

        for (Place p : places) {
            recommendPlaceRepository.save(new RecommendPlace(user, p));
        }
    }

    @Transactional
    public void requestPlantRecommend(Long userId, RecommendPlantRequestDTO request) {
        // TODO

        // 추천 작물 받기

    }
}
