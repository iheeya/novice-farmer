package com.d207.farmer.service.user;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.dto.myplant.*;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.FarmTodoRepository;
import com.d207.farmer.utils.FastApiUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPlantService {

    private final FarmRepository farmRepository;
    private final FarmTodoRepository farmTodoRepository;
    private final FastApiUtil fastApiUtil;

    @Transactional
    public String startGrowPlant(Long userId, StartGrowPlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        farm.startGrow(request.getStep());
        return "작물 키우기 시작하기 성공";
    }

    @Transactional
    public String deletePlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        farm.delete();
        return "작물 삭제 성공";
    }

    @Transactional
    public String harvestPlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        farm.harvest();
        return "작물 첫수확 성공";
    }

    @Transactional
    public String endPlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        farm.end();
        return "작물 키우기 종료하기 성공";
    }

    @Transactional
    public String waterPlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        // TODO todo에 추가가 아니라 원래 있던 todo를 update 해야할 것 같음

        return "작물 물주기 성공";
    }

    @Transactional
    public String fertilizerPlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        // TODO todo에 추가가 아니라 원래 있던 todo를 update 해야할 것 같음
        return "작물 비료주기 성공";
    }

    @Transactional
    public String updateName(Long userId, UpdatePlantNameRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        farm.updateName(request.getPlantName());
        return "이름 변경 성공";
    }

    @Transactional
    public String updateMemo(Long userId, UpdatePlantMemoRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        farm.updateMemo(request.getMemo());
        return "메모 변경 성공";
    }

    public InspectionPestResponseDTO inspectionPest(Long userId, InspectionPlantRequestDTO request) {
        InspectionPestResponseByFastApiDTO response = fastApiUtil.getInspectionPest(request.getImagePath());
        // TODO response에서 병해충 이름 받아서 몽고db 조회 후 반환
        return null;
    }

    public InspectionGrowthStepResponseDTO inspectionGrowthStep(Long userId, InspectionPlantRequestDTO request) {
        InspectionGrowthStepResponseByFastApiDTO response = fastApiUtil.getInspectionGrowthStep(request.getImagePath());
        return null;
    }

    @Transactional
    public String updateGrowthStepByInspection(Long userId, UpdateGrowthStepRequestDTO request) {
        Farm farm = farmRepository.findById(request.getMyPlantId()).orElseThrow();
        farm.updateGrowthStep(request.getGrowthStep());
        return "생장단계 업데이트 성공";
    }
}
