package com.d207.farmer.service.plant;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.plant.PlantGrowthIllust;
import com.d207.farmer.dto.plant.RegisterPlantIllustRequestDTO;
import com.d207.farmer.exception.plant.FailedRegisterPlantIllustException;
import com.d207.farmer.repository.plant.PlantIllustRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlantIllustService {

    private final PlantIllustRepository plantIllustRepository;
    private final PlantRepository plantRepository;

    @Transactional
    public String registerPlantIllust(RegisterPlantIllustRequestDTO request) {
        PlantGrowthIllust plantGrowthIllust = plantIllustRepository.findByPlantIdAndGrowthStep(request.getPlantId(), request.getGrowthStep()).orElse(null);
        if(plantGrowthIllust != null) {
            throw new FailedRegisterPlantIllustException("이미 등록되었습니다.");
        }
        Plant plant = plantRepository.findById(request.getPlantId()).orElseThrow();
        PlantGrowthIllust plantIllust = new PlantGrowthIllust(request);
        plantIllustRepository.save(plantIllust);
        plant.setPlantGrowthIllusts(plantIllust);
        return "작물 일러스트 등록 완료";
    }
}
