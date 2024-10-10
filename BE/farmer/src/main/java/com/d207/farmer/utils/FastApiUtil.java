package com.d207.farmer.utils;

import com.d207.farmer.domain.common.Address;
import com.d207.farmer.domain.farm.TodoType;
import com.d207.farmer.domain.place.Place;
import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.dto.common.FastAPIConnectTestResponseDTO;
import com.d207.farmer.dto.farm.get.RecommendPlaceRequestDTO;
import com.d207.farmer.dto.farm.get.RecommendPlaceResponseDTO;
import com.d207.farmer.dto.farm.get.RecommendPlantRequestDTO;
import com.d207.farmer.dto.farm.get.RecommendPlantResponseDTO;
import com.d207.farmer.dto.myplant.InspectionGrowthStepResponseByFastApiDTO;
import com.d207.farmer.dto.myplant.InspectionPestResponseByFastApiDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class FastApiUtil {

    @Value("${external.api.fastAPI.url}")
    private String fastApiUrl;

    public RecommendPlaceResponseDTO getRecommendPlaceByFastApi(RecommendPlaceRequestDTO request, Plant plant) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // json data
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("plantId", request.getPlantId());
        requestBody.put("plantName", plant.getName());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        List<RecommendPlaceResponseDTO.placeDTO> placeDTO = new ArrayList<>();
        placeDTO.add(new RecommendPlaceResponseDTO.placeDTO(1L));
        return new RecommendPlaceResponseDTO(placeDTO);

        // FIXME FastAPI에서 추천 API 만들어질 때까지, 베란다만 추천으로 만들어서 리턴
        /*
        String url = fastApiUrl + "/place/recommend";
        // 요청 및 응답
        ResponseEntity<RecommendPlaceResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                RecommendPlaceResponseDTO.class
        );

        // 응답 오류 처리
        if (!response.getStatusCode().is2xxSuccessful()) {
          throw new IllegalStateException("장소 추천 api 오류");
        }

        return response.getBody();
         */
    }

    public RecommendPlantResponseDTO getRecommendPlantByFastApi(Place place, Address address, Map<String, String> latAndLong) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // json data
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("place", new RecommendPlantResponseDTO.placeRequestDTO(place.getId(), place.getName()));
        requestBody.put("placeName", new RecommendPlantResponseDTO.addressRequestDTO(address.getSido(), address.getSigugun(), address.getBname1(), address.getBname2(),
                address.getBunji(), address.getJibun(), address.getZonecode(), latAndLong.get("latitude"), latAndLong.get("longitude")));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        List<RecommendPlantResponseDTO.plantDTO> plantDTO = new ArrayList<>();
        plantDTO.add(new RecommendPlantResponseDTO.plantDTO(1L));
        return new RecommendPlantResponseDTO(plantDTO);

        // FIXME FastAPI에서 추천 API 만들어질 때까지, 토마토만 추천으로 만들어서 리턴
        /*
        String url = fastApiUrl + "/plant/recommend";
        // 요청 및 응답
        ResponseEntity<RecommendPlantResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                RecommendPlantResponseDTO.class
        );

        // 응답 오류 처리
        if (!response.getStatusCode().is2xxSuccessful()) {
          throw new IllegalStateException("작물 추천 api 오류");
        }

        return response.getBody();
         */
    }

    public InspectionPestResponseByFastApiDTO getInspectionPest(String imagePath) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // json data
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("imagePath", imagePath);

        log.info("file imagePath = {}", imagePath);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        String url = fastApiUrl + "/plant/pest";
        // 요청 및 응답
        ResponseEntity<InspectionPestResponseByFastApiDTO> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                InspectionPestResponseByFastApiDTO.class
        );

        // 응답 오류 처리
        if (!response.getStatusCode().is2xxSuccessful()) {
          throw new IllegalStateException("병해충 검사 api 오류");
        }

        return response.getBody();
    }

    public InspectionGrowthStepResponseByFastApiDTO getInspectionGrowthStep(String imagePath) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // json data
//        Map<String, Object> requestBody = new HashMap<>();
//        requestBody.put("imagePath", imagePath);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        String url = fastApiUrl + "/plant/growth";

        // 요청 및 응답
        ResponseEntity<InspectionGrowthStepResponseByFastApiDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                InspectionGrowthStepResponseByFastApiDTO.class
        );

        // 응답 오류 처리
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("생장정보 업데이트 api 오류");
        }

        return response.getBody();
    }

    public FastAPIConnectTestResponseDTO testFastApiConnect() {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        String url = fastApiUrl + "/items/1";

        // 요청 및 응답
        ResponseEntity<FastAPIConnectTestResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                FastAPIConnectTestResponseDTO.class
        );

        // 응답 오류 처리
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("테스트 api 오류");
        }

        return response.getBody();
    }

    public String updateTodo(Long farmId, TodoType todoType) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // json data
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("farmId", farmId);
        requestBody.put("todoType", todoType);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(headers);

        String url = fastApiUrl + "/plant/todo";

        // 요청 및 응답
        ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                String.class
        );

        // 응답 오류 처리
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new IllegalStateException("TODO 업데이트 api 오류");
        }

        return response.getBody();
    }
}
