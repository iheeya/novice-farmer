package com.d207.farmer.dto.myplant;

import com.d207.farmer.domain.farm.TodoType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MyPlantInfoResponseDTO {
    private Boolean isStarted;
    private Boolean isAlreadyFirstHarvest;
    private PlantInfoDTO plantInfo;
    private List<TodoInfoDTO> todos;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PlantInfoDTO {
        private String placeName;
        private Long myPlaceId;
        private String myPlaceName;
        private String plantName;
        private String myPlantName;
        private String plantImagePath;
        private String startDate;
        private int plantGrowthStep;
        private int plantDegreeRatio;
        private ThresholdDTO threshold;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoInfoDTO {
        private String todoDate;
        private TodoType todoType;
        private int remainDay;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ThresholdDTO {
        private int step1;
        private int step2;
        private int step3;
    }
}
