package com.d207.farmer.service.user;

import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.repository.user.FavoritePlaceRepository;
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
public class FavoritePlaceService {
    private final FavoritePlaceRepository favoritePlaceRepository;
    private final UserRepository userRepository;

    public List<FavoritePlace> getPlaceById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        List<FavoritePlace> favoritePlants = favoritePlaceRepository.findByUser(user);

        return favoritePlants;
    }
}
