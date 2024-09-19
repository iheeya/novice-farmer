package com.d207.farmer.service.plant;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.dto.plant.PlantRegisterRequestDTO;
import com.d207.farmer.dto.plant.PlantResponseDTO;
import com.d207.farmer.dto.plant.PlantResponseWithIdDTO;
import com.d207.farmer.repository.plant.PlantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;

    @Transactional
    public String registerPlant(PlantRegisterRequestDTO request) {
        Plant plant = new Plant(request);
        plantRepository.save(plant);
        return plant.getName() + " 생성 완료";
    }

    public List<PlantResponseDTO> getAllPlants() {
        List<Plant> plants = plantRepository.findAll();
        return plants.stream().map(PlantResponseDTO::new).collect(Collectors.toList());
    }

    public List<PlantResponseWithIdDTO> getAllPlantsWithFalse() {
        List<Plant> plants = plantRepository.findAll();
        return plants.stream().map(PlantResponseWithIdDTO::new).collect(Collectors.toList());
    }


    public PlantResponseDTO getPlantById(Long id) {
        Optional<Plant> optPlant = plantRepository.findById(id);
        return optPlant.map(PlantResponseDTO::new).orElse(null);
    }
}
