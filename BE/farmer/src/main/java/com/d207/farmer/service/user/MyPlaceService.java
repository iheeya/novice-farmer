package com.d207.farmer.service.user;

import com.d207.farmer.domain.farm.UserPlace;
import com.d207.farmer.dto.myplace.MyPlaceRequestDTO;
import com.d207.farmer.dto.myplace.MyPlaceResponseDTO;
import com.d207.farmer.dto.myplace.UpdateMyPlaceNameRequestDTO;
import com.d207.farmer.repository.farm.UserPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPlaceService {

    private final UserPlaceRepository userPlaceRepository;

    public MyPlaceResponseDTO getMyPlace(Long userId, MyPlaceRequestDTO request) {
        // TODO
        return null;
    }

    public String updateMyPlaceName(Long userId, UpdateMyPlaceNameRequestDTO request) {
        UserPlace userPlace = userPlaceRepository.findById(request.getUserPlaceId()).orElseThrow();
        userPlace.modifyName(request.getUserPlaceName());
        return "장소 이름 변경 성공";
    }
}
