package com.d207.farmer.utils;

import com.d207.farmer.domain.plant.Plant;
import com.d207.farmer.dto.farm.get.RecommendPlaceRequestDTO;
import com.d207.farmer.dto.farm.get.RecommendPlaceResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
        // 요청 및 응답
        ResponseEntity<RecommendPlaceResponseDTO> response = restTemplate.exchange(
                fastApiUrl,
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
}
