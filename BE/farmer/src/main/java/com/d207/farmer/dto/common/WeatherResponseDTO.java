package com.d207.farmer.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResponseDTO {
    private ResponseDTO response;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResponseDTO {
        private HeaderDTO header;
        private BodyDTO body;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HeaderDTO {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BodyDTO {
        private String dataType;
        private List<ItemDTO> items;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemDTO {
        private String baseDate;
        private String baseTime;
        private String category;
        private String fcstDate;
        private String fcstTime;
        private String fcstValue;
        private String nx;
        private String ny;
    }
}
