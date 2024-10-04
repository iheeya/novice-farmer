package com.d207.farmer.service.mongo;

import com.d207.farmer.domain.mongo.MongoPlaceInfo;
import com.d207.farmer.domain.mongo.MongoPlantInfo;
import com.d207.farmer.dto.common.FileDirectory;
import com.d207.farmer.dto.mongo.place.*;
import com.d207.farmer.repository.mongo.MongoFertilizerRepository;
import com.d207.farmer.repository.mongo.MongoPestRepository;
import com.d207.farmer.repository.mongo.MongoPlaceRepository;
import com.d207.farmer.repository.mongo.MongoPlantRepository;
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
    private final MongoPlantRepository mongoPlantRepository;
    private final MongoFertilizerRepository mongoFertilizerRepository;
    private final MongoPestRepository mongoPestRepository;

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

    public List<TypeInfoResponseDTO> getPlaceTypeInfo() {
        List<TypeInfoResponseDTO> result = new ArrayList<>();

        List<MongoPlaceInfo> places = mongoPlaceRepository.findAll();

        List<PlaceDetail> contents = getContents(places);

        for (PlaceDetail c : contents) {
            result.add(new TypeInfoResponseDTO(c.getName(), "자세히 보기", MONGO.toString().toLowerCase() + "/" + c.getImages().get(0)));
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

    public ImagesAndContentsResponseDTO getPlaceInfo(InfoPlaceNameRequestDTO request) {
        List<MongoPlaceInfo> places = mongoPlaceRepository.findAll(); // FIXME 이름 조회로 최적화 가능

        List<PlaceDetail> PlaceContents = getContents(places);
        for (PlaceDetail pc : PlaceContents) {
            if(pc.getName().equals(request.getPlaceName())) {
                List<ImagesAndContentsResponseDTO.ContentDTO> contents = new ArrayList<>();
                contents.add(new ImagesAndContentsResponseDTO.ContentDTO(pc.getName(), pc.getDescription()));

                List<String> images = pc.getImages();
                images = addPrefixImages(images);

                List<String> suitableCrops = pc.getSuitableCrops();
                StringBuilder suitableCrop = new StringBuilder();
                for (String crop : suitableCrops) {
                    suitableCrop.append(crop).append(", ");
                }
                contents.add(new ImagesAndContentsResponseDTO.ContentDTO("키울만한 작물", suitableCrop.substring(0, suitableCrop.length() - 2)));

                return new ImagesAndContentsResponseDTO(images, contents);
            }
            break;
        }

        return new ImagesAndContentsResponseDTO();
    }

    private List<String> addPrefixImages(List<String> images) {
        List<String> result = new ArrayList<>();
        for(int i = 0; i < images.size(); i++) {
            result.add(MONGO.toString().toLowerCase() + "/" + images.get(i));
        }
        return result;
    }

    public List<TypeInfoResponseDTO> getPlantTypeInfo() {
        List<TypeInfoResponseDTO> result = new ArrayList<>();
        List<MongoPlantInfo> plants = mongoPlantRepository.findAll();
        for (MongoPlantInfo plant : plants) {
            result.add(new TypeInfoResponseDTO(plant.getName(), plant.getName() + "에 대해서 알아보세요",
                    MONGO.toString().toLowerCase() + "/" + plant.getImages().get(0)));
        }
        // 고추, 옥수수
        result.add(new TypeInfoResponseDTO("고추", "고추에 대해서 알아보세요", ""));
        result.add(new TypeInfoResponseDTO("옥수수", "옥수수에 대해서 알아보세요", ""));

        // 비료, 병해충
        result.add(new TypeInfoResponseDTO("비료", "비료들을 알아보세요", ""));
        result.add(new TypeInfoResponseDTO("병해충", "병해충들을 알아보세요", ""));

        return result;
    }
}
