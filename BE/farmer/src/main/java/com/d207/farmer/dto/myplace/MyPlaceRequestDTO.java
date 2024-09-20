package com.d207.farmer.dto.myplace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class MyPlaceRequestDTO {
    private PlaceInfoDTO placeInfo;
    private List<MyPlaceFarm> farms;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class PlaceInfoDTO {
        private Long placeId;
        private String placeName;
        private String myPlaceName;
        private int farmCount;
        private String whether;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class MyPlaceFarm {
        private Long plantId;
        private String plantName;
        private Long myPlantId;
        private String myPlantName;
        private int myPlantGrowthStep;
        private String imagePath;
        private String todoInfo;
        private LocalDateTime seedDate;
    }
}
