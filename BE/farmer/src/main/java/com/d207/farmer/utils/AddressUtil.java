package com.d207.farmer.utils;

import com.d207.farmer.dto.farm.api.GeoAPIResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Slf4j
@Component
public class AddressUtil {

    @Value("${external.api.naver.apigw.id}")
    private String apigwId;

    @Value("${external.api.naver.apigw.key}")
    private String apigwKey;

    /**
     * 주소로 위경도 구하기
     */
    public Map<String, String> getLatAndLongByJibun(String query) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-NCP-APIGW-API-KEY-ID", apigwId);
        headers.set("X-NCP-APIGW-API-KEY", apigwKey);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 파라미터 포함 url 생성
        String url = "https://naveropenapi.apigw.ntruss.com/map-geocode/v2/geocode?query=" + query;

        // 요청 및 응답
        ResponseEntity<GeoAPIResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                GeoAPIResponseDTO.class
        );

        // 응답 처리
        if(response.getStatusCode().is2xxSuccessful()) {
            Map<String, String> result = new HashMap<>();
            result.put("latitude", response.getBody().getAddresses().get(0).getY());
            result.put("longitude", response.getBody().getAddresses().get(0).getX());
            return result;
        } else {
            throw new IllegalStateException("네이버 지오코딩 api 호출 실패");
        }
    }

    /**
     * 지번주소의 번지 찾기
     */
    public String findBunjiByJibun(String jibun) {
        StringTokenizer st = new StringTokenizer(jibun, " ");
        String bunji = "";
        while(st.hasMoreTokens()) {
            bunji = st.nextToken();
        }
        return bunji;
    }
}
