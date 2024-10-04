package com.d207.farmer.service.mongo;

import com.d207.farmer.domain.mongo.MongoFertilizerInfo;
import com.d207.farmer.domain.mongo.MongoPlaceInfo;
import com.d207.farmer.domain.mongo.MongoPlantInfo;
import com.d207.farmer.dto.common.FileDirectory;
import com.d207.farmer.dto.mongo.place.*;
import com.d207.farmer.dto.mongo.plant.*;
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

    public ImagesAndContentsResponseDTO getPlaceInfo(InfoNameRequestDTO request) {
        List<MongoPlaceInfo> places = mongoPlaceRepository.findAll(); // FIXME 이름 조회로 최적화 가능

        List<PlaceDetail> PlaceContents = getContents(places);
        for (PlaceDetail pc : PlaceContents) {
            if(pc.getName().equals(request.getName())) {
                List<ImagesAndContentsResponseDTO.ContentDTO> contents = new ArrayList<>();
                contents.add(new ImagesAndContentsResponseDTO.ContentDTO(pc.getName(), pc.getDescription()));

                List<String> images = pc.getImages();
                images = addPrefixImages(images);

                List<String> suitableCrops = pc.getSuitableCrops();
                String suitableCrop = getStringConcatByComma(suitableCrops, "");

                contents.add(new ImagesAndContentsResponseDTO.ContentDTO("키울만한 작물", suitableCrop));

                return new ImagesAndContentsResponseDTO(images, contents);
            }
            break;
        }

        return new ImagesAndContentsResponseDTO();
    }

    private static String getStringConcatByComma(List<String> list, String suffix) {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s).append(suffix).append(", ");
        }
        return sb.substring(0, sb.length() - 2);
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

    public ImagesAndContentsResponseDTO getPlantInfo(InfoNameRequestDTO request) {
        MongoPlantInfo plant = mongoPlantRepository.findByName(request.getName());

        List<String> images = plant.getImages();
        images = addPrefixImages(images);

        List<ImagesAndContentsResponseDTO.ContentDTO> contents = new ArrayList<>();
        contents.add(new ImagesAndContentsResponseDTO.ContentDTO(plant.getName(), plant.getDefinition()));

        String bestSeason = getStringConcatByComma(plant.getBestSeason(), "월");
        contents.add(new ImagesAndContentsResponseDTO.ContentDTO("키우기 좋은 달", bestSeason));

        OptimalTemperature ot = plant.getOptimalTemperature();
        String optimalTemperature = ot.getMin() + "℃ ~ " + ot.getMax() + "℃";
        contents.add(new ImagesAndContentsResponseDTO.ContentDTO("적정 온도", optimalTemperature));

        Planting planting = plant.getPlanting();
        contents.add(new ImagesAndContentsResponseDTO.ContentDTO("재배법", planting.getMethod() + " - " + planting.getDescription()));

        List<GrowthStage> growthStages = plant.getFertilizerInfo().getGrowthStages();
        StringBuilder fertilizerInfo = new StringBuilder();
        for (GrowthStage gs : growthStages) {
            fertilizerInfo.append(gs.getStage()).append(": ").append(gs.getFertilizer().getType()).append("(").append(gs.getFertilizer().getBrand()).append(") -> ");
        }
        contents.add(new ImagesAndContentsResponseDTO.ContentDTO("비료 정보", fertilizerInfo.substring(0, fertilizerInfo.length() - 3)));

        List<PestOfPlant> pests = plant.getPests();
        StringBuilder pestInfo = new StringBuilder();
        for (PestOfPlant p : pests) {
            pestInfo.append(p.getName()).append("(").append(p.getDescription()).append("), ")
                    .append("예방(").append(p.getPrevention()).append("), ")
                    .append("치료(").append(p.getTreatment()).append(")");
        }
        contents.add(new ImagesAndContentsResponseDTO.ContentDTO("병해충 정보", pestInfo.toString()));

        contents.add(new ImagesAndContentsResponseDTO.ContentDTO("추가 정보", plant.getAdditionalInfo()));

        return new ImagesAndContentsResponseDTO(images, contents);
    }

    public List<TypeInfoResponseDTO> getFertilizerTypeInfo() {
        List<TypeInfoResponseDTO> result = new ArrayList<>();

        List<MongoFertilizerInfo> fertilizers = mongoFertilizerRepository.findAll();

        for (MongoFertilizerInfo fertilizer : fertilizers) {
            result.add(new TypeInfoResponseDTO(fertilizer.getComponent(), "자세히 보기",
                    MONGO.toString().toLowerCase() + "/" + fertilizer.getImages().get(0)));
        }

        // 샘플 2개
        result.add(new TypeInfoResponseDTO("제2인산칼슘", "자세히 보기", ""));
        result.add(new TypeInfoResponseDTO("제3인산칼슘", "자세히 보기", ""));

        return result;
    }
}
