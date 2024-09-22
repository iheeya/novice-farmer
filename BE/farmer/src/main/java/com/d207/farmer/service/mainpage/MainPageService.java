package com.d207.farmer.service.mainpage;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.user.FavoritePlace;
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

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalField;
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
        // db 조회
        List<Farm> farms = farmRepository.findByUserIdWithCurrentGrowing(userId).orElse(null);
        List<FavoritePlant> favoritePlants = favoritePlantRepository.findByUserId(userId);
        List<FavoritePlace> favoritePlaces = favoritePlaceRepository.findByUserId(userId);

        TodoInfoComponentDTO todoInfoComponent = getTodoInfo(userId);
        BeginnerInfoComponentDTO beginnerInfoComponent = getBeginnerInfo(userId, farms, favoritePlants); // 완성
        MyFarmListInfoComponentDTO myFarmListInfoComponent = getMyFarmListInfo(userId, farms); // 완성
        FarmGuideInfoComponentDTO farmGuideInfoComponent = getFarmGuideInfo(userId); // 완성
        FavoritesInfoComponentDTO favoritesInfoComponent = getFavoritesInfo(userId, farms, favoritePlants, favoritePlaces); // 완성
        MyPlantInfoComponentDTO myPlantInfoComponent = getMyPlantInfo(userId, farms); // 완성
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
    private BeginnerInfoComponentDTO getBeginnerInfo(Long userId, List<Farm> farms, List<FavoritePlant> favoritePlants) {
        // 키우는 작물이 없고 선호 작물이 없으면
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
    private MyFarmListInfoComponentDTO getMyFarmListInfo(Long userId, List<Farm> farms) {
        if(farms == null) {
            return new MyFarmListInfoComponentDTO(true, new ArrayList<>());
        }

        List<MyFarmListInfoComponentDTO.myFarmDTO> myFarms = new ArrayList<>();
        for (Farm farm : farms) {
            myFarms.add(new MyFarmListInfoComponentDTO.myFarmDTO(farm.getPlant().getId(),farm.getPlant().getName(), farm.getId(), farm.getMyPlantName()));
        }
        return new MyFarmListInfoComponentDTO(true, myFarms);
    }

    /**
     * 4. 텃밭 가이드 컴포넌트
     */
    private FarmGuideInfoComponentDTO getFarmGuideInfo(Long userId) {
        // 설문조사 여부와 관계없이 텃밭을 아예 만든 적이 없을 때
        List<Farm> farms = farmRepository.findByUserId(userId).orElse(null);
        return farms == null ? new FarmGuideInfoComponentDTO(true) : new FarmGuideInfoComponentDTO(false);
    }

    /**
     * 5. 선호 작물/장소 컴포넌트
     */
    private FavoritesInfoComponentDTO getFavoritesInfo(Long userId, List<Farm> farms, List<FavoritePlant> favoritePlants, List<FavoritePlace> favoritePlaces) {
        // 현재 키우고 있는 텃밭이 없으면
        if(farms != null) {
            return new FavoritesInfoComponentDTO(false, new ArrayList<>(), new ArrayList<>());
        }
        int componentSize = 0;
        List<FavoritesInfoComponentDTO.FavoritePlantDTO> favoritePlantDTOs = new ArrayList<>();
        for (FavoritePlant fp : favoritePlants) {
            if(++componentSize > 2) break;
            favoritePlantDTOs.add(new FavoritesInfoComponentDTO.FavoritePlantDTO(fp.getPlant().getId(), fp.getPlant().getName()));
        }

        componentSize = 0;
        List<FavoritesInfoComponentDTO.FavoritePlaceDTO> favoritePlaceDTOs = new ArrayList<>();
        for (FavoritePlace fp : favoritePlaces) {
            if(++componentSize > 2) break;
            favoritePlaceDTOs.add(new FavoritesInfoComponentDTO.FavoritePlaceDTO(fp.getPlace().getId(), fp.getPlace().getName()));
        }
        return new FavoritesInfoComponentDTO(true, favoritePlantDTOs, favoritePlaceDTOs);
    }

    /**
     * 6. 내 작물 정보 컴포넌트
     */
    private MyPlantInfoComponentDTO getMyPlantInfo(Long userId, List<Farm> farms) {
        // 키우고 있는 작물이 있으면 그 작물 중 랜덤이거나 제일 관리를 많이 한
        if(farms == null) {
            return new MyPlantInfoComponentDTO(false, null, null);
        }

        // 일단 랜덤으로
        Collections.shuffle(farms, new Random(LocalDateTime.now().toInstant(ZoneOffset.UTC).toEpochMilli()));
        Farm farm = farms.get(0);
        return new MyPlantInfoComponentDTO(true, farm.getPlant().getId(), farm.getPlant().getName());
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
