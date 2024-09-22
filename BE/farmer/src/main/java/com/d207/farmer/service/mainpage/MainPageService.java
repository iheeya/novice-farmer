package com.d207.farmer.service.mainpage;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.dto.mainpage.MainPageResponseDTO;
import com.d207.farmer.dto.mainpage.components.*;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.FavoritePlaceForFarmRepository;
import com.d207.farmer.repository.farm.FavoritePlantForFarmRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MainPageService {

    private final FarmRepository farmRepository;
    private final FavoritePlaceForFarmRepository favoritePlaceRepository;
    private final FavoritePlantForFarmRepository favoritePlantRepository;
    private final PlantRepository plantRepository;

    public MainPageResponseDTO getMainPage(Long userId) {
        TodoInfoComponentDTO todoInfoComponent = getTodoInfo(userId);
        BeginnerInfoComponentDTO beginnerInfoComponent = getBeginnerInfo(userId); // 완성
        MyFarmListInfoComponentDTO myFarmListInfoComponent = getMyFarmListInfo(userId);
        FarmGuideInfoComponentDTO farmGuideInfoComponent = getFarmGuideInfo(userId);
        FavoritesInfoComponentDTO favoritesInfoComponent = getFavoritesInfo(userId);
        MyPlantInfoComponentDTO myPlantInfoComponent = getMyPlantInfo(userId);
        RecommendInfoComponentDTO recommendInfoComponent = getRecommendInfo(userId);
        CommunityInfoComponentDTO communityInfoComponent = getCommunityInfo(userId);
        WeekendFarmComponentDTO weekendFarmComponent = getWeekendFarm(userId);
        return new MainPageResponseDTO();
    }

    /**
     * 1. TO DO 컴포넌트
     */
    private TodoInfoComponentDTO getTodoInfo(Long userId) {
        return null;
    }

    /**
     * 2. 초보자용 컴포넌트
     */
    private BeginnerInfoComponentDTO getBeginnerInfo(Long userId) {
        // 키우는 작물이 없고 선호 작물이 없으면
        List<Farm> farms = farmRepository.findByUserIdWithCurrentGrowing(userId).orElse(null);
        List<FavoritePlant> favoritePlants = favoritePlantRepository.findByUserId(userId);
        if (farms != null || favoritePlants != null) {
            return new BeginnerInfoComponentDTO(false, new ArrayList<>());
        }

        List<BeginnerInfoComponentDTO.plantInfoDTO> plantInfoDTOs = new ArrayList<>();
        plantInfoDTOs.add(new BeginnerInfoComponentDTO.plantInfoDTO(1L, "토마토", "초보 농부가 키우기 쉬운 토마토!"));
        plantInfoDTOs.add(new BeginnerInfoComponentDTO.plantInfoDTO(3L, "상추", "수확 주기가 짧아서 키우는 맛이 나는 상추!"));

        return new BeginnerInfoComponentDTO(true, plantInfoDTOs);
    }

    /**
     * 3. 내 텃밭 컴포넌트
     */
    private MyFarmListInfoComponentDTO getMyFarmListInfo(Long userId) {
        return null;
    }

    /**
     * 4. 텃밭 가이드 컴포넌트
     */
    private FarmGuideInfoComponentDTO getFarmGuideInfo(Long userId) {
        return null;
    }

    /**
     * 5. 선호 작물/장소 컴포넌트
     */
    private FavoritesInfoComponentDTO getFavoritesInfo(Long userId) {
        return null;
    }

    /**
     * 6. 내 작물 정보 컴포넌트
     */
    private MyPlantInfoComponentDTO getMyPlantInfo(Long userId) {
        return null;
    }

    /**
     * 7. 추천 컴포넌트
     */
    private RecommendInfoComponentDTO getRecommendInfo(Long userId) {
        return null;
    }

    /**
     * 8. 커뮤니티 컴포넌트
     */
    private CommunityInfoComponentDTO getCommunityInfo(Long userId) {
        return null;
    }

    /**
     * 9. 주말농장 컴포넌트
     */
    private WeekendFarmComponentDTO getWeekendFarm(Long userId) {
        return null;
    }
}
