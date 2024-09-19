package com.d207.farmer.service.farm;

import com.d207.farmer.domain.common.Address;
import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.*;
import com.d207.farmer.dto.farm.api.GeoAPIResponseDTO;
import com.d207.farmer.dto.farm.get.PlaceWithFavoriteResponseDTO;
import com.d207.farmer.dto.farm.get.PlaceWithRecommendAndFavoriteResponseDTO;
import com.d207.farmer.dto.farm.get.PlantWithFavoriteResponseDTO;
import com.d207.farmer.dto.farm.get.PlantWithRecommendAndFavoriteResponseDTO;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    @Value("${external.api.naver.apigw.id}")
    private String apigwId;

    @Value("${external.api.naver.apigw.key}")
    private String apigwKey;

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
        Map<String, String> latAndLongMap = getLatAndLongByJibun(request.getPlace().getAddress().getJibun());
        String latitude = latAndLongMap.get("latitude");
        String longitude = latAndLongMap.get("longitude");

        Address address = request.getPlace().getAddress();
        // 내장 값타입은 값 변경할 때 생성자로 인스턴스 새로 생성
        // 지번에서 번지 찾아서 address 인스턴스 재생성
        Address newAddress = createAddress(address);

        // userPlace 생성
        return new UserPlace(user, place, latitude, longitude, place.getName(), newAddress, request.getPlace().getDirection());
    }

    private Map<String, String> getLatAndLongByJibun(String query) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-APIGW-API-KEY-ID", apigwId);
        headers.set("X-NCP-APIGW-API-KEY", apigwKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 파라미터 포함 url 생성
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + query;

        // 요청 및 응답
        ResponseEntity<GeoAPIResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GeoAPIResponseDTO.class
        );


        // 응답 처리
        if(response.getStatusCode().is2xxSuccessful()) {
            Map<String, String> result = new HashMap<>();
            result.put("latitude", response.getBody().getAddresses().get(0).getY());
            result.put("longitude", response.getBody().getAddresses().get(0).getX());
            return result;
        } else {
            throw new IllegalStateException("네이버 지오코딩 api 호출 실패");
        }

    }

    private Address createAddress(Address addr) {
        // 번지는 지번의 마지막 토큰 값으로 넣기
        // 우편번호 api에서 번지는 제공해주지 않음
        // 대신 띄어쓰기 형식이 핏하게 맞아서 토크나이저로 마지막 토큰만 가져오면 됨
        String jibun = addr.getJibun();
        StringTokenizer st = new StringTokenizer(jibun, " ");
        String bunji = "";
        while(st.hasMoreTokens()) {
            bunji = st.nextToken();
        }
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
            result.add(new PlaceWithFavoriteResponseDTO(p.getId(), p.getName(), isFavorite));
        }
        result.sort(Comparator.comparing(PlaceWithFavoriteResponseDTO::getIsFavorite).reversed()
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
            result.add(new PlantWithFavoriteResponseDTO(p.getId(), p.getName(), isFavorite));
        }

        result.sort(Comparator.comparing(PlantWithFavoriteResponseDTO::getIsFavorite).reversed()
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
            result.add(new PlaceWithRecommendAndFavoriteResponseDTO(p.getId(), p.getName(), isFavorite, isRecommend));
        }

        result.sort(Comparator.comparing(PlaceWithRecommendAndFavoriteResponseDTO::getIsRecommend).reversed()
                .thenComparing(PlaceWithRecommendAndFavoriteResponseDTO::getIsFavorite).reversed()
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
            result.add(new PlantWithRecommendAndFavoriteResponseDTO(p.getId(), p.getName(), isFavorite, isRecommend));
        }

        result.sort(Comparator.comparing(PlantWithRecommendAndFavoriteResponseDTO::getIsRecommend).reversed()
                .thenComparing(PlantWithRecommendAndFavoriteResponseDTO::getIsFavorite).reversed()
                .thenComparing(PlantWithRecommendAndFavoriteResponseDTO::getPlantId));

        return result;
    }
}
