package com.d207.farmer.service.common;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.domain.user.FavoritePlace;
import com.d207.farmer.domain.user.FavoritePlant;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.survey.SurveyRegisterRequestDTO;
import com.d207.farmer.dto.user.UserInfoResponseDTO;
import com.d207.farmer.dto.user.UserRegisterRequestDTO;
import com.d207.farmer.dto.user.sample.UserSampleRegisterRequestDTO;
import com.d207.farmer.dto.utils.OnlyId;
import com.d207.farmer.repository.place.PlaceRepository;
import com.d207.farmer.repository.plant.PlantRepository;
import com.d207.farmer.repository.user.FavoritePlaceRepository;
import com.d207.farmer.repository.user.FavoritePlantRepository;
import com.d207.farmer.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SampleService {

    private final UserRepository userRepository;
    private final PlantRepository plantRepository;
    private final PlaceRepository placeRepository;
    private final FavoritePlantRepository favoritePlantRepository;
    private final FavoritePlaceRepository favoritePlaceRepository;

    @Transactional
    public User registerUser(UserSampleRegisterRequestDTO request) {
        User user = new User(request);
        return userRepository.save(user);
    }

    @Transactional
    public void registerFavorites(User user, SurveyRegisterRequestDTO request) {
        List<OnlyId> plantDTOs = request.getPlant();
        List<OnlyId> placeDTOs = request.getPlace();

        List<Long> plantIds = plantDTOs.stream().map(OnlyId::getId).toList();
        List<Plant> plants = plantRepository.findByIdIn(plantIds);
        for (Plant plant : plants) {
            favoritePlantRepository.save(new FavoritePlant(user, plant));
        }

        List<Long> placeIds = placeDTOs.stream().map(OnlyId::getId).toList();
        List<Place> places = placeRepository.findByIdIn(placeIds);
        for (Place place : places) {
            favoritePlaceRepository.save(new FavoritePlace(user, place));
        }
    }
}
