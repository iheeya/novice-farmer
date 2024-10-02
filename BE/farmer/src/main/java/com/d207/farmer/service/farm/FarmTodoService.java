package com.d207.farmer.service.farm;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.FarmTodo;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.dto.farm.todo.FarmTodoResponseDTO;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.FarmTodoRepository;
import com.d207.farmer.utils.DateUtil;
import com.d207.farmer.utils.FarmUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FarmTodoService {

    private final FarmRepository farmRepository;
    private final FarmTodoRepository farmTodoRepository;
    private final FarmUtil farmUtil;
    private final DateUtil dateUtil;

    public List<FarmTodoResponseDTO> getMyFarmTodo(Long userId) {
        List<FarmTodoResponseDTO> result = new ArrayList<>();

        List<Farm> farms = farmRepository.findByUserIdWithCurrentGrowing(userId).orElseThrow();

        List<Long> farmIds = farms.stream().map(Farm::getId).toList();
        List<FarmTodo> farmTodos = farmTodoRepository.findByFarmIdInAndIsCompletedFalseOrderByTodoDate(farmIds);
        // farmTodo 맵으로 만들기
        
        // place 정보 설정
        Map<Long, FarmTodoResponseDTO> resultMap = new HashMap<>(); // <key, value> = <userPlaceId, FarmTodoResponseDTO>
        for (Farm farm : farms) {
            UserPlace userPlace = farm.getUserPlace();
            Place place = userPlace.getPlace();
            Long userPlaceId = userPlace.getId();
            if(!resultMap.containsKey(userPlaceId)) {
                resultMap.put(userPlaceId, new FarmTodoResponseDTO(place.getId(), place.getName(), userPlaceId, userPlace.getName(), "", new ArrayList<>()));
            }
            FarmTodoResponseDTO dto = resultMap.get(userPlaceId);
            Plant plant = farm.getPlant();
            int growthStep = farmUtil.getGrowthStep(farm);
            List<FarmTodoResponseDTO.PlantDTO> plants = dto.getPlants();
            plants.add(new FarmTodoResponseDTO.PlantDTO(plant.getId(), plant.getName(), farm.getId(), farm.getMyPlantName(), growthStep,
                    dateUtil.degreeDayToRatio(farm.getDegreeDay(), plant.getDegreeDay()), new ArrayList<>()));
            
            
        }

        return result;
    }

}
