package com.d207.farmer.dto.farm.get;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendPlantResponseDTO {
    private List<plantDTO> plants;

    @Getter
    @AllArgsConstructor
    public static class plantDTO {
        private Long plantId;
    }

    @Getter
    @AllArgsConstructor
    public static class placeRequestDTO {
        private Long placeId;
        private String placeName;
    }

    @Getter
    @AllArgsConstructor
    public static class addressRequestDTO {
        private String sido;
        private String sigungu;
        private String bname1;
        private String bname2;
        private String bunji;
        private String jibun;
        private String zonecode;
        private String latitude;
        private String longitide;
    }
}
