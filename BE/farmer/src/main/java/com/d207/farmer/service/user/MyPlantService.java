package com.d207.farmer.service.user;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.dto.myplant.ManagePlantRequestDTO;
import com.d207.farmer.dto.myplant.StartGrowPlantRequestDTO;
import com.d207.farmer.repository.farm.FarmRepository;
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
        // FIXME todo에 추가가 아니라 원래 있던 todo를 update 해야하나?
        return "작물 물주기 성공";
    }

    @Transactional
    public String fertilizerPlant(Long userId, ManagePlantRequestDTO request) {
        Farm farm = farmRepository.findById(request.getFarmId()).orElseThrow();
        // FIXME todo에 추가가 아니라 원래 있던 todo를 update 해야하나?
        return "작물 비료주기 성공";
    }
}
