package com.d207.farmer.dto.mainpage.components;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
// @NoArgsConstructor
public class RecommendInfoComponentDTO {
    private Boolean isUsable;
    private String comment;
    private List<RecommendPlantDTO> recommendPlants;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    private static class RecommendPlantDTO {
        private Long plantId;
        private String plantName;
    }

    public RecommendInfoComponentDTO() {
        recommendPlants = new ArrayList<>();
        recommendPlants.add(new RecommendPlantDTO());
        recommendPlants.add(new RecommendPlantDTO());
    }
}
