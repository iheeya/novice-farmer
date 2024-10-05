package com.d207.farmer.dto.myplant;

import com.d207.farmer.domain.farm.TodoType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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
        private LocalDate startDate;
        private int plantGrowthStep;
        private int plantDegreeRatio;
        private ThresholdDTO threshold;
        private LocalDate firstHarvestDate;
        private LocalDate recentWateringDate;
        private LocalDate recentFertilizingDate;
        private String memo;

        // 시작하지 않았을 때 용
        public PlantInfoDTO(String placeName, Long myPlaceId, String myPlaceName, String plantName, String myPlantName, int plantGrowthStep, int plantDegreeRatio, ThresholdDTO threshold, String memo) {
            this.placeName = placeName;
            this.myPlaceId = myPlaceId;
            this.myPlaceName = myPlaceName;
            this.plantName = plantName;
            this.myPlantName = myPlantName;
            this.plantGrowthStep = plantGrowthStep;
            this.plantDegreeRatio = plantDegreeRatio;
            this.threshold = threshold;
            this.memo = memo;
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoInfoDTO {
        private LocalDate todoDate;
        private TodoType todoType;
        private int remainDay;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ThresholdDTO {
        private Integer totalStep;
        private Integer step1;
        private Integer step2;
        private Integer step3;
    }
}
