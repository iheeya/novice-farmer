package com.d207.farmer.service.place;

import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.dto.place.PlaceRegisterRequestDTO;
import com.d207.farmer.dto.place.PlaceResponseDTO;
import com.d207.farmer.dto.place.PlaceResponseWithIdDTO;
import com.d207.farmer.dto.plant.PlantResponseWithIdDTO;
import com.d207.farmer.repository.place.PlaceRepository;
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
public class PlaceService {

    private final PlaceRepository placeRepository;

    @Transactional
    public String registerPlace(PlaceRegisterRequestDTO request) {
        Place place = new Place(request);
        placeRepository.save(place);
        return place.getName() + " 생성 완료";
    }

    public List<PlaceResponseDTO> getAllPlaces() {
        List<Place> places = placeRepository.findAll();
        return places.stream().map(PlaceResponseDTO::new).collect(Collectors.toList());
    }

    public PlaceResponseDTO getPlaceById(Long id) {
        Optional<Place> optPlace = placeRepository.findById(id);
        return optPlace.map(PlaceResponseDTO::new).orElse(null);
    }

    public List<PlaceResponseWithIdDTO> getAllPlacesWithFalse() {

        List<Place> plants = placeRepository.findAll();

        return plants.stream().map(PlaceResponseWithIdDTO::new).collect(Collectors.toList());


    }
}
