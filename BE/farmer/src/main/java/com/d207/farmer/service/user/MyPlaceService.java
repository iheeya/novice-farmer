package com.d207.farmer.service.user;

import com.d207.farmer.dto.myplace.MyPlaceRequestDTO;
import com.d207.farmer.dto.myplace.MyPlaceResponseDTO;
import com.d207.farmer.dto.myplace.UpdateMyPlaceNameRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyPlaceService {
    public MyPlaceResponseDTO getMyPlace(Long userId, MyPlaceRequestDTO request) {
        // TODO
        return null;
    }

    public String updateMyPlaceName(Long userId, UpdateMyPlaceNameRequestDTO request) {
        // TODO
        return null;
    }
}
