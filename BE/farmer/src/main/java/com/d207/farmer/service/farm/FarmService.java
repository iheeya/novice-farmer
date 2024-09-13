package com.d207.farmer.service.farm;

import com.d207.farmer.domain.farm.Farm;
import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.user.User;
import com.d207.farmer.dto.farm.register.FarmRegisterRequestDTO;
import com.d207.farmer.repository.farm.FarmRepository;
import com.d207.farmer.repository.place.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FarmService {

    private final FarmRepository farmRepository;
    private final PlaceRepository placeRepository;

    public String registerFarm(User user, FarmRegisterRequestDTO request) {
        Place place = placeRepository.findById(request.getPlace().getPlaceId()).orElseThrow();
        String latitude = "";
        String longitude = "";
        UserPlace userPlace = new UserPlace(user, place, request.getPlace().getLocation(), latitude, longitude);
        Farm farm = new Farm(user, request); // TODO
        farmRepository.save(farm);
        return farm.getUserPlace().getName() + ", " + farm.getMyPlantName() + " 생성 완료";
    }
}
