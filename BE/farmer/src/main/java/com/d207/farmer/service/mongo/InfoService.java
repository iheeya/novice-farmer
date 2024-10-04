package com.d207.farmer.service.mongo;

import com.d207.farmer.domain.mongo.MongoPlaceInfo;
import com.d207.farmer.dto.common.FileDirectory;
import com.d207.farmer.dto.mongo.place.PlaceDetail;
import com.d207.farmer.dto.mongo.place.PlaceInfoResponseDTO;
import com.d207.farmer.dto.mongo.place.PlaceTypeInfoResponseDTO;
import com.d207.farmer.repository.mongo.MongoPlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.d207.farmer.dto.common.FileDirectory.MONGO;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InfoService {

    private final MongoPlaceRepository mongoPlaceRepository;

    public PlaceInfoResponseDTO getPlaceMainInfo() {
        List<MongoPlaceInfo> places = mongoPlaceRepository.findAll();
        Map<String, String> contents = new HashMap<>();
        for (MongoPlaceInfo place : places) {
            if(place.getHeading().equals("종류")) continue;
            contents.put(place.getHeading(), place.getContent());
        }
        PlaceInfoResponseDTO.InfoDetailDTO justice = new PlaceInfoResponseDTO.InfoDetailDTO(
            "텃밭이란?", "텃밭은 무엇을 말하는 걸까요?", contents.get("정의")
        );

        PlaceInfoResponseDTO.InfoDetailDTO purpose = new PlaceInfoResponseDTO.InfoDetailDTO(
          "텃밭의 목적", "텃밭을 왜 가꿔야 하나요?", contents.get("목적")
        );

        PlaceInfoResponseDTO.InfoDetailDTO effect = new PlaceInfoResponseDTO.InfoDetailDTO(
            "텃밭의 효과", "텃밭을 가꿨을 때의 효과는?", contents.get("효과")
        );

        PlaceInfoResponseDTO.InfoDetailDTO placeType = new PlaceInfoResponseDTO.InfoDetailDTO(
            "텃밭의 종류", "텃밭의 종류를 알아보세요", ""
        );

        return new PlaceInfoResponseDTO(justice, purpose, effect, placeType);
    }

    public List<PlaceTypeInfoResponseDTO> getPlaceTypeInfo() {
        List<PlaceTypeInfoResponseDTO> result = new ArrayList<>();

        List<MongoPlaceInfo> places = mongoPlaceRepository.findAll();

        List<PlaceDetail> contents = getContents(places);

        for (PlaceDetail c : contents) {
            result.add(new PlaceTypeInfoResponseDTO(c.getName(), "자세히 보기", MONGO.toString().toLowerCase() + "/" + c.getImages().get(0)));
        }

        return result;
    }

    private List<PlaceDetail> getContents(List<MongoPlaceInfo> places) {
        for (MongoPlaceInfo place : places) {
            if(place.getHeading().equals("종류")) {
                return place.getContents();
            }
        }
        return new ArrayList<>();
    }
}
