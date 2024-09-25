package com.d207.farmer.dto.myplant;

import com.d207.farmer.domain.farm.TodoType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
        private Long myPlaceId;
        private String myPlaceName;
        private String myPlantName;
        private int myPlantGrowthStep;
        private String plantName;
        private String placeName;
        private String plantImagePath;
        private LocalDateTime startDate;
        private LocalDateTime curDate;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TodoInfoDTO {
        private LocalDateTime todoDate;
        private TodoType todoType;
        private int remainDay;
    }
}
