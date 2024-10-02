package com.d207.farmer.service.farm;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.FarmTodo;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.dto.farm.todo.FarmTodoResponseDTO;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.farm.FarmTodoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FarmTodoService {

    private final FarmRepository farmRepository;
    private final FarmTodoRepository farmTodoRepository;

    public List<FarmTodoResponseDTO> getMyFarmTodo(Long userId) {
        // TODO
        List<FarmTodoResponseDTO> result = new ArrayList<>();

        List<Farm> farms = farmRepository.findByUserIdWithCurrentGrowing(userId).orElseThrow();

        List<Long> userPlaceIds = farms.stream().map(f -> f.getUserPlace().getId()).toList(); // dto에 미리 place들 을 채워놓기위함
        Map<Long, Integer> userPlaceIndMap = new HashMap<>(); // <key, value> = <userPlaceId, index>

        for (Farm farm : farms) {
            Long userPlaceId = farm.getUserPlace().getId();

        }

        List<Long> farmIds = farms.stream().map(Farm::getId).toList();
        List<FarmTodo> farmTodos = farmTodoRepository.findByFarmIdInAndIsCompletedFalseOrderByTodoDate(farmIds);



        return result;
    }

}
