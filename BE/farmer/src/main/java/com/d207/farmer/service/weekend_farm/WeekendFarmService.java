package com.d207.farmer.service.weekend_farm;

import com.d207.farmer.domain.user.User;
import com.d207.farmer.domain.weekend_farm.WeekendFarm;
import com.d207.farmer.dto.weekend_farm.WeekendFarmRegisterRequestDTO;
import com.d207.farmer.dto.weekend_farm.WeekendFarmResponseDTO;
import com.d207.farmer.repository.user.UserRepository;
import com.d207.farmer.repository.weekend_farm.WeekendFarmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WeekendFarmService {

    private final WeekendFarmRepository weekendFarmRepository;
    private final UserRepository userRepository;

    @Transactional
    public String registerWeekendFarm(WeekendFarmRegisterRequestDTO request) {
        WeekendFarm weekendFarm = new WeekendFarm(request);
        weekendFarmRepository.save(weekendFarm);
        return weekendFarm.getName() + " 생성 완료";
    }

    public List<WeekendFarmResponseDTO> getAllWeekendFarms() {
        List<WeekendFarm> weekendFarms = weekendFarmRepository.findAll();
        return weekendFarms.stream().map(WeekendFarmResponseDTO::new).collect(Collectors.toList());
    }

    public WeekendFarmResponseDTO getWeekendFarmById(Long id) {
        Optional<WeekendFarm> optWeekendFarm = weekendFarmRepository.findById(id);
        return optWeekendFarm.map(WeekendFarmResponseDTO::new).orElse(null);
    }

    public List<WeekendFarmResponseDTO> getWeekendFarmRecommend(Long userId) {
        // TODO user 지역 기반으로 주말농장 추천
//        User user = userRepository.findById(userId).orElseThrow();
        List<WeekendFarm> weekendFarms = weekendFarmRepository.findAll();
        return weekendFarms.stream().map(WeekendFarmResponseDTO::new).collect(Collectors.toList());
    }
}
