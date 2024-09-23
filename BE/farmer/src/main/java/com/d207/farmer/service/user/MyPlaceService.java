package com.d207.farmer.service.user;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import com.d207.farmer.dto.myplace.*;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.UserPlaceRepository;
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

    public MyPlaceResponseDTO getMyPlace(Long userId, MyPlaceRequestDTO request) {
        UserPlace userPlace = userPlaceRepository.findByIdWithPlace(request.getUserPlaceId()).orElseThrow();
        List<Farm> farms = farmRepository.findByUserPlaceIdWithCurrentGrowing(request.getUserPlaceId()).orElse(null);
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
            int myPlantGrowthStep = farm.getGrowthStep();

            String imagePath = "";
            // 일러스트 이미지 경로 불러오기
            // FIXME 쿼리 겁나 나갈듯 -> 수정 필요
            for (PlantGrowthIllust pgi : farm.getPlant().getPlantGrowthIllusts()) {
                if(pgi.getStep() == myPlantGrowthStep) {
                    imagePath = pgi.getImagePath();
                    break;
                }
            }

            String todoInfo = "5일 후에 물을 줘야 해요"; // FIXME TODO 정보 어떻게 받아올지
            LocalDateTime seedDate = farm.getSeedDate();
            myPlaceFarmDTOs.add(new MyPlaceFarmDTO(plantId, plantName, myPlantId, myPlantName,
                    myPlantGrowthStep, imagePath, todoInfo, seedDate));
        }

        return new MyPlaceResponseDTO(placeInfoDTO, myPlaceFarmDTOs);
    }

    @Transactional
    public String updateMyPlaceName(Long userId, UpdateMyPlaceNameRequestDTO request) {
        UserPlace userPlace = userPlaceRepository.findById(request.getUserPlaceId()).orElseThrow();
        userPlace.modifyName(request.getUserPlaceName());
        return "장소 이름 변경 성공";
    }
}
