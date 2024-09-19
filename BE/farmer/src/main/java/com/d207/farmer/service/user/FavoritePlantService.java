package com.d207.farmer.service.user;

import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.repository.user.FavoritePlantRepository;
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
