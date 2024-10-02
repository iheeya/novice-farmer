package com.d207.farmer.service.user;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import com.d207.farmer.domain.plant.PlantThreshold;
import com.d207.farmer.dto.myplace.*;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.UserPlaceRepository;
import com.d207.farmer.repository.plant.PlantIllustRepository;
import com.d207.farmer.repository.plant.PlantThresholdRepository;
import com.d207.farmer.utils.DateUtil;
import com.d207.farmer.utils.FarmUtil;
import com.d207.farmer.utils.UserAuthUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPlaceService {

    private final UserPlaceRepository userPlaceRepository;
    private final FarmRepository farmRepository;
    private final DateUtil dateUtil;
    private final UserAuthUtil userAuthUtil;
    private final FarmUtil farmUtil;

    public MyPlaceResponseDTO getMyPlace(Long userId, Long myPlaceId) {
        UserPlace userPlace = userPlaceRepository.findByIdWithPlace(myPlaceId).orElseThrow();
        userAuthUtil.authorizationUser(userId, userPlace); // 회원 일치 여부
        List<Farm> farms = farmRepository.findByUserPlaceIdWithCurrentGrowing(myPlaceId).orElse(null);
        int farmCount = farms == null ? 0 : farms.size();

        // FIXME 날씨 정보 어떻게 받아올지
        PlaceInfoDTO placeInfoDTO = new PlaceInfoDTO(userPlace.getPlace().getId(), userPlace.getPlace().getName(), userPlace.getName(),
                farmCount, "오늘 비가 올 예정입니다");

        if(farms == null) {
            // 장소는 있는데 현재 키우고 있는 작물이 없을 때(없었거나, 종료했거나, 삭제했거나)
            return new MyPlaceResponseDTO(placeInfoDTO, new ArrayList<>());
        }


        List<MyPlaceFarmDTO> myPlaceFarmDTOs = new ArrayList<>();
        for(Farm farm : farms) {
            Long plantId = farm.getPlant().getId();
            String plantName = farm.getPlant().getName();
            Long myPlantId = farm.getId();
            String myPlantName = farm.getMyPlantName();

            int myPlantGrowthStep = farmUtil.getGrowthStep(farm);

            String imagePath = "";
            // 일러스트 이미지 경로 불러오기
            for (PlantGrowthIllust pgi : farm.getPlant().getPlantGrowthIllusts()) {
                if(pgi.getStep() == myPlantGrowthStep) {
                    imagePath = pgi.getImagePath();
                    break;
                }
            }

            String todoInfo = "5일 후에 물을 줘야 해요"; // FIXME TODO 정보 어떻게 받아올지
            LocalDateTime seedDate = farm.getSeedDate();
            myPlaceFarmDTOs.add(new MyPlaceFarmDTO(plantId, plantName, myPlantId, myPlantName,
                    myPlantGrowthStep, imagePath, todoInfo, seedDate == null ? null : seedDate.toLocalDate()));
        }

        return new MyPlaceResponseDTO(placeInfoDTO, myPlaceFarmDTOs);
    }

    @Transactional
    public String updateMyPlaceName(Long userId, UpdateMyPlaceNameRequestDTO request) {
        UserPlace userPlace = userPlaceRepository.findById(request.getUserPlaceId()).orElseThrow();
        userAuthUtil.authorizationUser(userId, userPlace); // 회원 일치 여부
        userPlace.modifyName(request.getUserPlaceName());
        return "장소 이름 변경 성공";
    }
}
