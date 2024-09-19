package com.d207.farmer.service.plant;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.plant.PlantResponseWithIdDTO;
import com.d207.farmer.repository.plant.FavoritePlantRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FavoritePlantService {
    private final FavoritePlantRepository favoritePlantRepository;
    private final UserRepository userRepository;

    public List<FavoritePlant> getPlantById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow();
        List<FavoritePlant> favoritePlants = favoritePlantRepository.findByUser(user);

        return favoritePlants;

    }
}
