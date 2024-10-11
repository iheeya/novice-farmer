package com.d207.farmer.utils;

import com.d207.farmer.dto.common.WeatherResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class WeatherUtil {

    @Value("${external.api.weather.url}")
    private String url;

    @Value("${external.api.weather.key}")
    private String serviceKey;

    private Map<String, WeatherPos> weatherPosMap = new HashMap<>();

    public WeatherUtil() {
        weatherPosMap.put("구미시", new WeatherPos(86, 95));
        weatherPosMap.put("대구광역시", new WeatherPos(86, 90));
    }

    public Integer getTemperatureAvg(String location) { // 구미시, 대구광역시
        LocalDate nowDate = LocalDate.now();
        String year = String.valueOf(nowDate.getYear());
        String month = String.valueOf(nowDate.getMonthValue());
        String day = String.valueOf(nowDate.getDayOfMonth());
        if(day.length() == 1) day = "0" + day;

        RestTemplate restTemplate = new RestTemplate();

        // 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 파라미터 포함 url 생성
        StringBuilder requestUrlBuilder = new StringBuilder();
        requestUrlBuilder.append(url).append("?ServiceKey=").append(serviceKey).append("&pageNo=1&numOfRows=266&dataType=JSON&base_date=")
                .append(year).append(month).append(day).append("&base_time=2300&nx=").
                append(weatherPosMap.get(location).nx).append("&ny=").append(weatherPosMap.get(location).ny);

        // 요청 및 응답
        ResponseEntity<WeatherResponseDTO> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                WeatherResponseDTO.class
        );

        // 응답 처리
        if(response.getStatusCode().is2xxSuccessful()) {
            List<WeatherResponseDTO.ItemDTO> items = response.getBody().getResponse().getBody().getItems();
            int minTem = 10;
            int maxTem = 30;
            for (WeatherResponseDTO.ItemDTO item : items) {
                if(item.getCategory().equals("TMN")) {
                    minTem = temperatureToIntValue(item.getFcstValue());
                }
                if(item.getCategory().equals("TMX")) {
                    maxTem = temperatureToIntValue(item.getFcstValue());
                    break;
                }
            }
            return (minTem + maxTem) / 2;
        } else {
            return Integer.MIN_VALUE;
        }
    }

    private int temperatureToIntValue(String value) {
        if(!value.contains(".")) return Integer.parseInt(value);
        int dotInd = value.lastIndexOf(".");
        return Integer.parseInt(value.substring(0, dotInd));
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class WeatherPos {
        private int nx;
        private int ny;
    }
}
